package it.sebastianosuraci.springboot.demo.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import it.sebastianosuraci.springboot.core.dto.BaseSerialDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentDTO extends BaseSerialDTO {	
	@NotBlank	
	protected String firstName;
	protected String lastName;
	@Email	
	protected String email;
	protected LocalDate birthDate;
	protected String schoolName;
	protected Integer schoolId;
}
