package it.sebastianosuraci.springboot.core.domain;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * Base class for all the entities in the domain.
 * 
 * @author Sebastiano
 * 
 * @param <T>
 *            The type of the primary id.
 */
@MappedSuperclass
public abstract class BaseEntity<K extends Serializable> {

	public abstract K getId();

	public abstract void setId(K id);

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		@SuppressWarnings("unchecked")
		BaseEntity<K> other = (BaseEntity<K>) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

	@Transient
	public boolean isNew() {
		return this.getId() == null;
	}

}