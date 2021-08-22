package it.sebastianosuraci.springboot.core.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.sebastianosuraci.springboot.core.domain.BaseEntity;
import it.sebastianosuraci.springboot.core.exception.AppException;
import it.sebastianosuraci.springboot.core.exception.AppException.ErrCode;
import it.sebastianosuraci.springboot.core.repository.BaseRepository;
import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
public abstract class BaseService<T extends BaseEntity<K>, K extends Serializable> {

	protected boolean readonly = false;

	final protected BaseRepository<T, K> repository;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
		
	protected void beforeInsert(T newEntity) throws AppException {

	}

	protected void afterInsert(T entity) {

	}
	protected T beforeUpdate(T updateEntity) throws AppException {
		return updateEntity;
	}

	protected void afterUpdate(T entity) {

	}

	protected void beforeDelete(T entity) throws AppException {

	}

	protected void afterDelete(T entity) throws AppException {

	}

	protected void checkExistsById(K id, FetchOptions fetchOptions) throws AppException {
		if (!repository.existsById(id, fetchOptions)) {
			throw new AppException(ErrCode.NOT_FOUND);
		}
	}

	@Transactional
	public void insert(T newEntity) throws AppException {
		beforeInsert(newEntity);
		repository.save(newEntity);
		afterInsert(newEntity);
	}

	@Transactional
	public void update(T updateEntity, FetchOptions fetchOptions) throws AppException {		
		checkExistsById(updateEntity.getId(), fetchOptions);
		updateEntity = beforeUpdate(updateEntity);
		repository.save(updateEntity);
		afterUpdate(updateEntity);
	}

	@Transactional
	public void delete(T deleteEntity, FetchOptions fetchOptions) throws AppException {
		checkExistsById(deleteEntity.getId(), fetchOptions);
		beforeDelete(deleteEntity);
		repository.delete(deleteEntity);
		afterDelete(deleteEntity);
	}

	@Transactional
	public void delete(K id, FetchOptions fetchOptions) throws AppException {
		checkExistsById(id, fetchOptions);
		repository.deleteById(id);
	}

	@Transactional
	public Optional<T> findById(K id, FetchOptions fetchOptions) throws AppException {
		Optional<T> entity = repository.findById(id, fetchOptions);
		afterFindById(entity);
		return entity;
	}

	@Transactional
	public boolean existsById(K id, FetchOptions fetchOptions) throws AppException {
		return repository.existsById(id, fetchOptions);
	}

	protected void afterFindById(Optional<T> entity) throws AppException {
		
	}

	public List<T> getList(FetchOptions fetchOptions) {
		return repository.getList(fetchOptions);
	}
	
}
