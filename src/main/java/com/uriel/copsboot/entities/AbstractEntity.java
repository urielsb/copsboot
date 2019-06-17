/**
 * 
 */
package com.uriel.copsboot.entities;

import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;

/**
 * @author Uriel Santoyo
 *
 */
@MappedSuperclass
public abstract class AbstractEntity<T extends EntityId> implements Entity<T> {

	@EmbeddedId
	private T id;
	
	protected AbstractEntity() {}
	
	public AbstractEntity(T id) {
		this.id = Objects.requireNonNull(id);
	}
	
	@Override
	public T getId() {
		return this.id;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		
		if(this == obj) {
			result = true;
		} else if (obj instanceof AbstractEntity) {
			@SuppressWarnings("rawtypes")
			AbstractEntity other = (AbstractEntity)obj;
			result = Objects.equals(this.id, other.id);
		}
		
		return result;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.id);
	}
	
}
