package it.sebastianosuraci.springboot.demo.domain;


import it.sebastianosuraci.springboot.core.domain.BaseEntitySerial;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Subject extends BaseEntitySerial {
	protected String code;
	protected String description;
}
