package it.sebastianosuraci.springboot.demo.domain;

import java.util.Date;

import it.sebastianosuraci.springboot.core.domain.BaseEntitySerial;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
//@EnrolmentValid
public class Enrolment extends BaseEntitySerial {

	protected Date enrolmentDate; 
	
	@ManyToOne
	Student student;
	
	@ManyToOne 
	Course course;
	
}
