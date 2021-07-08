package it.sebastianosuraci.springboot.demo.dto;

import java.util.List;

import it.sebastianosuraci.springboot.core.dto.BaseSerialDTO;
import it.sebastianosuraci.springboot.demo.domain.School.SchoolCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SchoolDTO extends BaseSerialDTO  {
    protected SchoolCategory category;

	protected String name;

    protected List<TeacherDTO> teacherList;
}
