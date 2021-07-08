package it.sebastianosuraci.springboot.demo.domain;

import javax.persistence.Entity;

import it.sebastianosuraci.springboot.core.domain.BaseEntitySerial;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Skill extends BaseEntitySerial {

	protected String name;

	
}
