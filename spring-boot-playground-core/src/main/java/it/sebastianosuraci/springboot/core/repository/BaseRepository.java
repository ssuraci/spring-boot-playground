package it.sebastianosuraci.springboot.core.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.repository.NoRepositoryBean;

import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphQuerydslPredicateExecutor;
import com.querydsl.core.BooleanBuilder;

import it.sebastianosuraci.springboot.core.domain.BaseEntity;
import it.sebastianosuraci.springboot.core.domain.QBaseEntity;
import it.sebastianosuraci.springboot.core.dto.PageModel;
import it.sebastianosuraci.springboot.core.exception.AppException;
import it.sebastianosuraci.springboot.core.exception.AppException.ErrCode;
import it.sebastianosuraci.springboot.core.service.FetchOptions;
import lombok.NonNull;


/**
 * This class represents a default repository for entities. By subclassing this interface and redefining a few methods, 
 * the following features of a complete and funcional CRUD implementation are automatically available:
 *   
 * - support for plain JPA, QueryDSL and EntityGraph
 * - unified and simplified handling of implicit user visibility filters
 * - support for filtering, ordering and paging item lists
 * 
 */

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity<K>, K extends Serializable> extends EntityGraphJpaRepository<T, K>, EntityGraphQuerydslPredicateExecutor<T> {

	/**
	 * Returns the predicate based on fetchOptions by adding to the query:
	 *  - user visibility filters
	 *  - filters defined in pageModel
	 * 
	 * @param builder
	 * @param fetchOptions
	 * @return
	 */
	default BooleanBuilder addFetchOptionsPredicate(@NonNull BooleanBuilder builder,FetchOptions fetchOptions) {
		return Optional.of(builder)
			.map(b -> fetchOptions.isUserPermFilter() ? addUserPermFilterPredicate(b) : b)
			.map(b -> fetchOptions.getPageModel() != null ? addPageModelFilterPredicate(b, fetchOptions.getPageModel()) : b)
			.get();
	}


	/**
	 * Default predicate when searching an entity by id. Should be overridden in case of composite keys.
	 * 
	 * @param id id to be searched
	 * @param fetchOptions FetchOptions (eg: wheter to filter by user visibility, etc)
	 * @return @BooleamBuilder predicate
	 */
	default BooleanBuilder addFindByIdPredicate(@NonNull K id) {
		BooleanBuilder builder = new BooleanBuilder(); 
		QBaseEntity baseEntity = getQBaseEntity();
		builder.and(baseEntity.id.eq(id)); 
		return builder;
	}

	/**
	 * Finds an entity by id, applying Entity Graph if defined in PageModel.
	 * 
	 * @param id id to be searched
	 * @param fetchOptions FetchOptions (eg: wheter to filter by user visibility, etc)
	 * @return @Optional with the entity
	 * @throws AppException
	 */
	default Optional<T> findById(@NonNull K id, @NonNull FetchOptions fetchOptions) throws AppException {
		// checks whether an EntityGraph is defined in PageModel
		Optional<EntityGraph> entityGraph = fetchOptions.getPageModel() != null ? getEntityGraph(fetchOptions.getPageModel().getFetchProfile()) : Optional.empty();

		// Builds query and loads records
		Iterable<T> res  =  Optional.of(addFindByIdPredicate(id))
			.map(builder -> addFetchOptionsPredicate(builder, fetchOptions))
			.map(builder -> entityGraph.isPresent() ? findAll(builder, entityGraph.get()) : findAll(builder)).get();
		return  StreamSupport.stream(res.spliterator(), false).findFirst();		
	}

	/**
	 * Finds an entity by id, throwing an @AppExceotion if entity was not found.
	 * 
	 * @param id id to be searched
	 * @param fetchOptions FetchOptions (eg: wheter to filter by user visibility, etc)
	 * @return @Optional with the entity
	 * @throws AppException
	 */
	default T findByIdException(@NonNull K id, @NonNull FetchOptions fetchOptions) throws AppException {
		return  findById(id, fetchOptions).orElseThrow(() -> new AppException(ErrCode.NOT_FOUND));		
	}

	/**
	 * Checks whether an entity exists by it's id.
	 * 
	 * @param id id to be searched
	 * @param fetchOptions FetchOptions (eg: wheter to filter by user visibility, etc)
	 * @return entity existance
	 */
	default boolean existsById(@NonNull K id, @NonNull FetchOptions fetchOptions) {
		return  Optional.of(addFindByIdPredicate(id))
			.map(builder -> addFetchOptionsPredicate(builder, fetchOptions))
			.map(this::exists).get();
	}

	/**
	 * Counts entity records.
	 * 
	 * @param fetchOptions FetchOptions (eg: wheter to filter by user visibility, etc)
	 * @return entity existance
	 */
	default long count(@NonNull FetchOptions fetchOptions) {
		return  Optional.of(new BooleanBuilder())
			.map(builder -> addFetchOptionsPredicate(builder, fetchOptions))
			.map(this::count).get();
	}

	/**
	 * Returns a paged list of entities, filtering by
	 * - user visibility filters
	 * - filters defined in pageModel
	 * 
	 * ordering by:
	 * - ordering defined in pageModel (if present) or default ordering
	 * 
	 * @param pageModel
	 * @param fetchOptions
	 * @return
	 */
	default List<T> getList(@NonNull FetchOptions fetchOptions) {
		Pageable pageable = getPageable(fetchOptions.getPageModel());
		Optional<EntityGraph> entityGraph = getEntityGraph(fetchOptions.getPageModel().getFetchProfile());

		Page<T> page  =  Optional.of(addFetchOptionsPredicate(new BooleanBuilder(), fetchOptions))
			.map(builder -> entityGraph.isPresent() ? findAll(builder, pageable, entityGraph.get()) : findAll(builder, pageable)).get();

		fetchOptions.getPageModel().setFetchedRows(page.getTotalElements());
		return page.getContent();
	}

	/**
	 * Get paging options based on pageModel
	 * @param pageModel
	 * @return
	 */

	default Pageable getPageable(PageModel pageModel) {
		List<Order> orderList = new ArrayList<>();
		List<String> allowedOrderByList = getAllowedOrderByList();
		if (pageModel == null) {
			pageModel = new PageModel();
		}
		if (pageModel.getSort() != null && pageModel.getSort().isEmpty() && !allowedOrderByList.isEmpty()) {
			pageModel.getSort().add(allowedOrderByList.get(0)); // not guaranteed to be the first
		}
		if (pageModel.getSort() != null) {
			for (String sortField : pageModel.getSort()) {
				Direction direction = Direction.ASC;
				if (sortField.startsWith("+")) {
					direction = Direction.ASC;
					sortField = sortField.substring(1);
				}
				if (sortField.startsWith("-")) {
					direction = Direction.DESC;
					sortField = sortField.substring(1);
				}
				if (allowedOrderByList.contains(sortField)) {
					orderList.add(new Order(direction, sortField));
				}
			}
		}
		Sort sort = Sort.by(orderList);
		return PageRequest.of(pageModel.getPageStart() - 1, pageModel.getPageItems(), sort);
	}

	/**
	 * This methos should be overridden to define user visibility filters
	 * @param builder
	 * @return
	 */
	default BooleanBuilder addUserPermFilterPredicate(BooleanBuilder builder) {
		return builder;
	}

	/**
	 * This method should be overriden for getting QBaseEntity
	 * @return
	 */
	QBaseEntity getQBaseEntity();

	/**
	 * This method should be overridden to return entity EntityGraph based on fetchProfile
	 * @param fetchProfile
	 * @return
	 */
	default Optional<EntityGraph> getEntityGraph(String fetchProfile) {
		return Optional.empty();
	}

	/**
	 * This method should be overridden to return predicates for filter defined in @pageModel
	 * @param fetchProfile
	 * @return
	 */
	default BooleanBuilder addPageModelFilterPredicate(BooleanBuilder builder, PageModel pageModel) {
		return builder;
	}

	/**
	 * This method should be overridden to return allowed order by properties
	 * @param fetchProfile
	 * @return
	 */
	default List<String> getAllowedOrderByList() {
		String[] s = { "id" };
		return Arrays.asList(s);
	}


}
