package it.sebastianosuraci.springboot.core.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import it.sebastianosuraci.springboot.core.domain.BaseEntity;
import it.sebastianosuraci.springboot.core.dto.PageModel;
import it.sebastianosuraci.springboot.core.exception.AppException;

public interface IBaseService<T extends BaseEntity<K>, K extends Serializable>  {
    public void save(T newEntity) throws AppException;

	public void delete(T entity) throws AppException;

	public void delete(K id) throws AppException;

	public Optional<T> findById(K id) throws AppException;
		
	public List<T> getList(PageModel pageModel);
	
	public List<T> findAllOrderById();
}
