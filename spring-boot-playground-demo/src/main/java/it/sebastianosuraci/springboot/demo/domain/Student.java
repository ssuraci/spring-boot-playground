package it.sebastianosuraci.springboot.demo.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

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

	@ManyToOne
	protected School school;
	
	@ManyToMany
	protected List<Skill> skills;
	
	@Transient
	public String getName() {
		return lastName + ' ' + firstName;
	}
}
