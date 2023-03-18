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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Course extends BaseEntity<Integer> {
    @Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
    @Column(name = "id",unique=true, nullable = false)
	protected Integer id;
	
	private String title;

	private String description;
	
	private Date startDate;

	private Date endDate;
	
	@ManyToOne
	Teacher teacher;
	
	@OneToMany(mappedBy="course")
	protected List<Enrolment> enrolledStudents;

}
