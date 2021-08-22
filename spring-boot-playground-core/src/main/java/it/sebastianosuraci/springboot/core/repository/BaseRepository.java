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

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphQuerydslPredicateExecutor;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

import it.sebastianosuraci.springboot.core.domain.BaseEntity;
import it.sebastianosuraci.springboot.core.domain.QBaseEntity;
import it.sebastianosuraci.springboot.core.dto.PageModel;
import it.sebastianosuraci.springboot.core.service.FetchOptions;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity<K>, K extends Serializable> extends EntityGraphJpaRepository<T, K>, EntityGraphQuerydslPredicateExecutor<T> {


	default BooleanBuilder findByIdPredicate(K id, FetchOptions fetchOptions) {
		BooleanBuilder builder = new BooleanBuilder(); 
		QBaseEntity baseEntity = getQBaseEntity();
		builder.and(baseEntity.id.eq(id)); 
		if (fetchOptions.isUserPermFilter()) {
			addUserPermFilterPredicate(builder);
		}
		return builder;
	}


	default Optional<T> findById(K id, FetchOptions fetchOptions) {
		return StreamSupport.stream(findAll(findByIdPredicate(id, fetchOptions)).spliterator(), false).findFirst();
	}

	default boolean existsById(K id, FetchOptions fetchOptions) {
		return exists(findByIdPredicate(id, fetchOptions));
	}


	default long count(FetchOptions fetchOptions) {
		return count(addFetchOptionsPredicate(new BooleanBuilder(), fetchOptions));
	}

	default BooleanBuilder addFetchOptionsPredicate(BooleanBuilder builder, FetchOptions fetchOptions) {
		addFilterPredicate(builder, fetchOptions.getPageModel());
		if (fetchOptions.isUserPermFilter()) {
			addUserPermFilterPredicate(builder);
		}
		return builder;
	}

	default List<T> getList(FetchOptions fetchOptions) {
		PageModel pageModel = fetchOptions.getPageModel() == null ? new PageModel() : fetchOptions.getPageModel();
		Predicate fetchOptionsPredicate = addFetchOptionsPredicate(new BooleanBuilder(), fetchOptions);
		Pageable pageable = getPageable(pageModel);
		Optional<EntityGraph> entityGraph = getEntityGraph(pageModel.getFetchProfile());
		Page<T> page = entityGraph.isPresent() ? findAll(fetchOptionsPredicate, pageable, entityGraph.get()) : findAll(fetchOptionsPredicate, pageable);
		pageModel.setFetchedRows(page.getNumberOfElements());
		return page.getContent();
	}



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

	default BooleanBuilder addUserPermFilterPredicate(BooleanBuilder builder) {
		return builder;
	}

	QBaseEntity getQBaseEntity();

	default Optional<EntityGraph> getEntityGraph(String fetchProfile) {
		return Optional.empty();
	}

	default BooleanBuilder addFilterPredicate(BooleanBuilder builder, PageModel pageModel) {
		return builder;
	}

	default List<String> getAllowedOrderByList() {
		String[] s = { "id" };
		return Arrays.asList(s);
	}


}
