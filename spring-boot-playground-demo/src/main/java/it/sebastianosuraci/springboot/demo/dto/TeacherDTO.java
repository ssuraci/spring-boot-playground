package it.sebastianosuraci.springboot.demo.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import it.sebastianosuraci.springboot.core.dto.BaseSerialDTO;
import it.sebastianosuraci.springboot.demo.domain.Teacher.TeacherCategory;
import it.sebastianosuraci.springboot.demo.validation.TeacherValid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@TeacherValid
public class TeacherDTO extends BaseSerialDTO {	
	@NotBlank	
	protected String firstName;
	protected String lastName;
	@Email	
	protected String email;
	protected LocalDate birthDate;
	protected TeacherCategory category;
	protected String schoolName;
	protected Integer schoolId;
	protected List<CourseDTO> courseList;
}
