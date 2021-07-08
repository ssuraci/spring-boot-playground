package it.sebastianosuraci.springboot.demo.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import it.sebastianosuraci.springboot.core.domain.BaseEntitySerial;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Student extends BaseEntitySerial {
	
	protected String firstName;
	protected String lastName;
	
	protected String email;
	
	protected Date birthDate;
		
	@OneToMany(mappedBy="student")
	protected List<Enrolment> enrolledCourses;

	@ManyToMany
	protected List<Skill> skills;
		
}
