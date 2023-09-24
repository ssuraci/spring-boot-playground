package it.sebastianosuraci.springboot.demo.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
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

	@NotBlank
	protected String address;

	protected String city;

	protected Double lat;

	protected Double lng;


	protected List<TeacherDTO> teacherList;
}
