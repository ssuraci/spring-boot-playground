package it.sebastianosuraci.springboot.core.service;

import java.io.Serializable;

import it.sebastianosuraci.springboot.core.domain.BaseEntity;
import it.sebastianosuraci.springboot.core.exception.AppException;

public interface IBaseService<T extends BaseEntity<K>, K extends Serializable>  extends IBaseReadOnlyService<T,K>  {
    public T insert(T entity) throws AppException;
	public T update(T updateEntity, FetchOptions fetchOptions) throws AppException;
	public void delete(K id, FetchOptions fetchOptions) throws AppException;

	public T update(T updateEntity) throws AppException;
	public void delete(K id) throws AppException;
	
}
