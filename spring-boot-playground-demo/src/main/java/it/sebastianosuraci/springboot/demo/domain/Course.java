package it.sebastianosuraci.springboot.demo.domain;

import java.util.Date;
import java.util.List;

import it.sebastianosuraci.springboot.core.domain.BaseEntitySerial;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
