package it.sebastianosuraci.springboot.demo.domain;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import it.sebastianosuraci.springboot.core.domain.BaseEntitySerial;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Teacher extends BaseEntitySerial {

	public enum TeacherCategory { TC_NULL, TC_JUNIOR, TC_SENIOR }
		
	protected String firstName;

	protected String lastName;

	protected String email;

	@Column(name="birth_date")
	protected LocalDate birthDate;
	
	@Enumerated(EnumType.STRING)
	protected TeacherCategory category=TeacherCategory.TC_JUNIOR;

	@OneToMany(mappedBy="teacher")
	protected Set<Course> courseList; 

	@ManyToOne
	protected School school;
	
	@Transient
	public String getName() {
		return lastName + ' ' + firstName;
	}
	
}
