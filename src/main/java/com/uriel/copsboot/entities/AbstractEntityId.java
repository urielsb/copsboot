package com.uriel.copsboot.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.MappedSuperclass;

import com.google.common.base.MoreObjects;

@MappedSuperclass
public abstract class AbstractEntityId<T extends Serializable> implements Serializable, EntityId<T> {

	private T id;
	
	protected AbstractEntityId() {}
	
	protected AbstractEntityId(T id) {
		this.id = Objects.requireNonNull(id);
	}
	
	@Override
	public T getId() {
		return this.id;
	}
	
	@Override
	public String asString() {
		return this.id.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		
		if(obj == this) {
			result = true;
		} else if(obj instanceof AbstractEntityId) {
			@SuppressWarnings("rawtypes")
			AbstractEntityId other = (AbstractEntityId)obj;
			result = Objects.equals(this.id, other.id);
		}
		
		return result;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.id);
	}
	
	@Override
	public String toString( ) {
		return MoreObjects.toStringHelper(this)
				.add("id", this.id)
				.toString();
	}
}
