package it.sebastianosuraci.springboot.demo.dto;

import java.util.Date;
import java.util.List;

import it.sebastianosuraci.springboot.core.dto.BaseSerialDTO;
import it.sebastianosuraci.springboot.demo.domain.Enrolment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseDTO extends BaseSerialDTO {

	private String title;

	private String description;
	
	private Date startDate;

	private Date endDate;
	
	private Integer teacherId;
	
	private String teacherName;

}
