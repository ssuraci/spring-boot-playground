package it.sebastianosuraci.springboot.demo.domain;

import java.util.Date;
import java.util.List;

import it.sebastianosuraci.springboot.core.domain.BaseEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Student extends BaseEntity<Integer> {
    @Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
    @Column(name = "id",unique=true, nullable = false)
	protected Integer id;
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
