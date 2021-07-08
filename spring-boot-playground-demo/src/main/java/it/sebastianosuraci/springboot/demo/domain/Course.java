package it.sebastianosuraci.springboot.demo.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import it.sebastianosuraci.springboot.core.domain.BaseEntitySerial;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Course extends BaseEntitySerial {

	private String title;

	private String description;
	
	private Date startDate;

	private Date endDate;
	
	@ManyToOne
	Teacher teacher;
	
	@OneToMany(mappedBy="course")
	protected List<Enrolment> enrolledStudents;

}
