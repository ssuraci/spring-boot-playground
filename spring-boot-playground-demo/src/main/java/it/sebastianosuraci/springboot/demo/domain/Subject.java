package it.sebastianosuraci.springboot.demo.domain;


import it.sebastianosuraci.springboot.core.domain.BaseEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Subject extends BaseEntity<Integer> {
    @Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
    @Column(name = "id",unique=true, nullable = false)
	protected Integer id;
	protected String code;
	protected String description;
}
