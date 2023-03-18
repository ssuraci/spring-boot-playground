package it.sebastianosuraci.springboot.demo.domain;

import java.time.LocalDate;
import java.util.Set;

import it.sebastianosuraci.springboot.core.domain.BaseEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Teacher extends BaseEntity<Integer> {
    @Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
    @Column(name = "id",unique=true, nullable = false)
	protected Integer id;

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
