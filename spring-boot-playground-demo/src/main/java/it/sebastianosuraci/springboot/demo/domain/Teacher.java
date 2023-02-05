package it.sebastianosuraci.springboot.demo.domain;

import java.time.LocalDate;
import java.util.Set;

import it.sebastianosuraci.springboot.core.domain.BaseEntitySerial;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
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

	@OneToMany(mappedBy="teacher")
	protected Set<Course> courseList; 

	@ManyToOne
	protected School school;

	@ManyToOne
	protected Subject subject;

	
	@Transient
	public String getName() {
		return lastName + ' ' + firstName;
	}
	
}
