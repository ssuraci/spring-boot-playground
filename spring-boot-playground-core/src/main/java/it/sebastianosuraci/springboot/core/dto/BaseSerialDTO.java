package it.sebastianosuraci.springboot.core.dto;

public class BaseSerialDTO implements BaseDTO<Integer> {
	protected Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
