package it.sebastianosuraci.springboot.core.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import it.sebastianosuraci.springboot.core.domain.BaseEntity;
import it.sebastianosuraci.springboot.core.exception.AppException;

public interface IBaseReadOnlyService<T extends BaseEntity<K>, K extends Serializable> {
	public Optional<T> findById(K id, FetchOptions fetchOptions) throws AppException;
	public List<T> getList(FetchOptions fetchOptions);
	public boolean existsById(K id, FetchOptions fetchOptions) throws AppException;
	public Optional<T> findById(K id) throws AppException;
	public boolean existsById(K id) throws AppException;
	public Long count(FetchOptions fetchOptions) throws AppException;
	
}
