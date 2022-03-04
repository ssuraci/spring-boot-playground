package it.sebastianosuraci.springboot.core.dto;

public interface BaseDTO <K> {
	
	K getId();

	void setId(K id);
}
