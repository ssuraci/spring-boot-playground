package it.sebastianosuraci.springboot.demo.domain;

import java.util.Set;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import it.sebastianosuraci.springboot.core.domain.BaseEntitySerial;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@NamedEntityGraph(name = "school.teacherList", attributeNodes = @NamedAttributeNode("teacherList"))
@NamedEntityGraph(
		name = "school.teacherListAndCourse", attributeNodes = 
		@NamedAttributeNode(value = "teacherList", subgraph = "subgraph.teacherCourse"), 
		subgraphs = {
				@NamedSubgraph(name = "subgraph.teacherCourse", 
						attributeNodes = @NamedAttributeNode(value = "courseList")) 
				})
public class School extends BaseEntitySerial {

	public enum SchoolCategory {
		SC_NULL, SC_PRIMARY, SC_SECONDARY, SC_HIGH
	}

	@Enumerated(EnumType.STRING)
	protected SchoolCategory category;

	protected String name;

	@OneToMany(mappedBy = "school")
	@Fetch(value = FetchMode.SUBSELECT)
	protected Set<Teacher> teacherList;

}
