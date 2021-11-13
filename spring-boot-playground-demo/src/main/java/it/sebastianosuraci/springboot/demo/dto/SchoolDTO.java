package it.sebastianosuraci.springboot.demo.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

	@NotBlank
    protected String name;

    protected List<TeacherDTO> teacherList;
}
