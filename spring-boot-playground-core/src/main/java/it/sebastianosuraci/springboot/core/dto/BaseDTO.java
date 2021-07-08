package it.sebastianosuraci.springboot.core.dto;

public interface BaseDTO <K> {
	
	public K getId();

	public void setId(K id);
}
