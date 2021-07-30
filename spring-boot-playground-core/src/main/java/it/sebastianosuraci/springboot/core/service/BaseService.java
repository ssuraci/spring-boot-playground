package it.sebastianosuraci.springboot.core.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;

import it.sebastianosuraci.springboot.core.domain.BaseEntity;
import it.sebastianosuraci.springboot.core.dto.PageModel;
import it.sebastianosuraci.springboot.core.exception.AppException;
import it.sebastianosuraci.springboot.core.exception.AppException.ErrCode;
import it.sebastianosuraci.springboot.core.repository.BaseRepository;
import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
public abstract class BaseService<T extends BaseEntity<K>, K extends Serializable> {

	protected boolean readonly = false;

	final protected BaseRepository<T, K> repository;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
		
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
		repository.save(newEntity);
		afterSave(newEntity);
	}

	@Transactional
	public void delete(T entity) throws AppException {
		beforeDelete(entity);
		repository.delete(entity);
		afterDelete(entity);
	}

	@Transactional
	public void delete(K id) throws AppException {
		T entity = findById(id).orElseThrow(() -> new AppException(ErrCode.VALIDATION, "entity not found"));
		repository.delete(entity);
	}

	@Transactional
	public Optional<T> findById(K id) throws AppException {
		Optional<T> entity = repository.findById(id);
		afterFindById(entity);
		return entity;
	}
	
	protected void afterFindById(Optional<T> entity) throws AppException {
		
	}

	
	public List<T> getList(PageModel pageModel) {
		return repository.getList(pageModel);
	}
	
	@Transactional
	public List<T> findAllOrderByOrd() {
		return repository.findAll(Sort.by("ord"));
	}

	@Transactional
	public List<T> findAllOrderById() {
		return repository.findAll(Sort.by("id"));
	}
}
