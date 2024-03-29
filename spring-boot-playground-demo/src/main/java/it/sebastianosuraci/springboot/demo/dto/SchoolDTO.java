package it.sebastianosuraci.springboot.demo.dto;

import jakarta.validation.constraints.NotNull;

import it.sebastianosuraci.springboot.core.dto.BaseSerialDTO;
import it.sebastianosuraci.springboot.demo.domain.School.SchoolCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SchoolDTO extends BaseSerialDTO  {
    @NotNull
    protected SchoolCategory category;

	protected String name;

	protected String address;
	
	protected String city;
	
	protected Double lat;
	
	protected Double lng;	
	
}
