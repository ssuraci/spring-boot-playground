package it.sebastianosuraci.springboot.core.service;

import java.io.Serializable;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import it.sebastianosuraci.springboot.core.domain.BaseEntity;
import it.sebastianosuraci.springboot.core.exception.AppException;
import it.sebastianosuraci.springboot.core.exception.AppException.ErrCode;
import it.sebastianosuraci.springboot.core.repository.BaseRepository;
import lombok.NonNull;



public abstract class BaseService<T extends BaseEntity<K>, K extends Serializable> extends BaseReadOnlyService <T,K> implements IBaseService<T,K> {

	@PersistenceContext
	protected EntityManager em;
		
	public BaseService(BaseRepository<T, K> repository) {
		super(repository);
	}

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

	@Transactional
	public T insert(@NonNull T newEntity) throws AppException {
		return insert(newEntity, defaultFetchOptions());
	}

	@Transactional
	public T update(@NonNull T updateEntity) throws AppException {
		return insert(updateEntity, defaultFetchOptions());
	}

	@Transactional
	public void delete(@NonNull K id) throws AppException {
		delete(id, defaultFetchOptions());
	}

	@Transactional
	public T insert(@NonNull T newEntity, @NonNull FetchOptions fetchOptions) throws AppException {
		beforeInsert(newEntity);
		repository.save(newEntity);
		afterInsert(newEntity);
		em.detach(newEntity); // force reload from DB
		return findById(newEntity.getId(), fetchOptions).orElseThrow(() -> new AppException(ErrCode.NOT_FOUND));
	}

	@Transactional
	public T update(@NonNull T updateEntity, @NonNull FetchOptions fetchOptions) throws AppException {		
		checkExistsById(updateEntity.getId(), fetchOptions);
		updateEntity = beforeUpdate(updateEntity);
		repository.save(updateEntity);
		afterUpdate(updateEntity);
		em.detach(updateEntity); // force reload from DB
		return findById(updateEntity.getId(), fetchOptions).orElseThrow(() -> new AppException(ErrCode.NOT_FOUND));
	}

	@Transactional
	public void delete(@NonNull K id, @NonNull FetchOptions fetchOptions) throws AppException {
		checkExistsById(id, fetchOptions);
		repository.deleteById(id);
	}
	
}
