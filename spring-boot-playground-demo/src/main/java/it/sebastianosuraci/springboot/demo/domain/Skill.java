package it.sebastianosuraci.springboot.demo.domain;

import it.sebastianosuraci.springboot.core.domain.BaseEntitySerial;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Skill extends BaseEntitySerial {

	protected String name;

	
}
