package it.sebastianosuraci.springboot.demo.dto;

import jakarta.validation.constraints.NotBlank;

import it.sebastianosuraci.springboot.core.dto.BaseSerialDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubjectDTO extends BaseSerialDTO  {
    
	@NotBlank
    protected String code;

	protected String description;
}
