package it.sebastianosuraci.springboot.core.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.sebastianosuraci.springboot.core.domain.BaseEntity;
import it.sebastianosuraci.springboot.core.exception.AppException;
import it.sebastianosuraci.springboot.core.exception.AppException.ErrCode;
import it.sebastianosuraci.springboot.core.repository.BaseRepository;
import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
public abstract class BaseReadOnlyService<T extends BaseEntity<K>, K extends Serializable> {

	final protected BaseRepository<T, K> repository;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected FetchOptions defaultFetchOptions() {
		return FetchOptions.builder().userPermFilter(true).build();
	}
	


	protected void checkExistsById(K id, FetchOptions fetchOptions) throws AppException {
		if (!repository.existsById(id, fetchOptions)) {
			throw new AppException(ErrCode.NOT_FOUND);
		}
	}

	public Optional<T> findById(K id, FetchOptions fetchOptions) throws AppException {
		Optional<T> entity = repository.findById(id, fetchOptions);
		afterFindById(entity);
		return entity;
	}

	public Optional<T> findById(K id) throws AppException {
		return findById(id, defaultFetchOptions());
	}

	public boolean existsById(K id, FetchOptions fetchOptions) throws AppException {
		return repository.existsById(id, fetchOptions);
	}
	public boolean existsById(K id) throws AppException {
		return existsById(id, defaultFetchOptions());
	}

	protected void afterFindById(Optional<T> entity) throws AppException {
		
	}

	public List<T> getList(FetchOptions fetchOptions) {
		return repository.getList(fetchOptions);
	}
	
	public Long count(FetchOptions fetchOptions) throws AppException {
		return repository.count(fetchOptions);
	}
}
