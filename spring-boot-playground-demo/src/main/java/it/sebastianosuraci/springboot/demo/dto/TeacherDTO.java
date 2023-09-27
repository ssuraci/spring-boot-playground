package it.sebastianosuraci.springboot.demo.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import it.sebastianosuraci.springboot.core.dto.BaseSerialDTO;
import it.sebastianosuraci.springboot.demo.domain.Teacher.TeacherCategory;
import it.sebastianosuraci.springboot.demo.validation.TeacherValid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TeacherValid
@Builder
public class TeacherDTO extends BaseSerialDTO {	
	@NotBlank	
	protected String firstName;
	protected String lastName;
	@Email	
	protected String email;
	protected LocalDate birthDate;
	protected String schoolName;
	@NotNull
	protected Integer schoolId;
	protected TeacherCategory category;
	protected String subjectDescription;
	@NotNull
	protected Integer subjectId;
	protected List<CourseDTO> courseList;
}
