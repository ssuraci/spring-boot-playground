package it.sebastianosuraci.springboot.core.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import it.sebastianosuraci.springboot.core.domain.BaseEntity;
import it.sebastianosuraci.springboot.core.dto.PageModel;
import it.sebastianosuraci.springboot.core.exception.AppException;
import it.sebastianosuraci.springboot.core.exception.AppException.ErrCode;
import it.sebastianosuraci.springboot.core.repository.BaseRepository;



public abstract class BaseService<T extends BaseEntity<K>, K extends Serializable> {

	protected boolean readonly = false;

	protected abstract BaseRepository<T, K> getBaseRepository();

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	protected Validator validator;

	
	protected void beforeSave(T newEntity) throws AppException {

	}

	protected void afterSave(T entity) {

	}

	protected void beforeDelete(T entity) throws AppException {

	}

	protected void afterDelete(T entity) throws AppException {

	}

	@Transactional
	public void save(T newEntity) throws AppException {
		beforeSave(newEntity);
		getBaseRepository().save(newEntity);
		afterSave(newEntity);
	}

	@Transactional
	public void delete(T entity) throws AppException {
		beforeDelete(entity);
		getBaseRepository().delete(entity);
		afterDelete(entity);
	}

	@Transactional
	public void delete(K id) throws AppException {
		T entity = findById(id).orElseThrow(() -> new AppException(ErrCode.VALIDATION, "entity not found"));
		getBaseRepository().delete(entity);
	}

	@Transactional
	public Optional<T> findById(K id) throws AppException {
		Optional<T> entity = getBaseRepository().findById(id);
		afterFindById(entity);
		return entity;
	}
	
	protected void afterFindById(Optional<T> entity) throws AppException {
		
	}

	
	public List<T> getList(PageModel pageModel) {
		return getBaseRepository().getList(pageModel);
	}
	
	@Transactional
	public List<T> findAllOrderByOrd() {
		return getBaseRepository().findAll(Sort.by("ord"));
	}

	@Transactional
	public List<T> findAllOrderById() {
		return getBaseRepository().findAll(Sort.by("id"));
	}
}
