package it.sebastianosuraci.springboot.core.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.repository.NoRepositoryBean;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphs;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphQuerydslPredicateExecutor;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

import it.sebastianosuraci.springboot.core.domain.BaseEntity;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity<K>, K extends Serializable> extends EntityGraphJpaRepository<T, K>, EntityGraphQuerydslPredicateExecutor<T> {

	default List<T> getList(PageModel pageModel) {
		var builder = new BooleanBuilder();
		Pageable pageable = getPageable(pageModel);
		Predicate query = buildCriteria(builder, pageModel);
		Page<T> page = null;
		if (StringUtils.isNotBlank(pageModel.getEntityGraph())) {
			page = findAll(query, pageable, EntityGraphs.named(pageModel.getEntityGraph()));
		} else {
			page = findAll(query, pageable);
		}
		pageModel.setFetchedRows(page.getNumberOfElements());
		return page.getContent();
	}

	default Predicate buildCriteria(BooleanBuilder builder, PageModel pageModel) {
		return builder;
	}

	default List<String> getAllowedOrderByList() {
		String[] s = { "id" };
		return Arrays.asList(s);
	}

	default Pageable getPageable(PageModel pageModel) {
		List<Order> orderList = new ArrayList<>();
		List<String> allowedOrderByList = getAllowedOrderByList();
		if (pageModel.getSort() != null && pageModel.getSort().isEmpty() && !allowedOrderByList.isEmpty()) {
			pageModel.getSort().add(allowedOrderByList.get(0)); // not guaranteed to be the first
		}
		if (pageModel.getSort() != null) {
			for (String sortField : pageModel.getSort()) {
				var direction = Direction.ASC;
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

}
