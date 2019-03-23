package com.uriel.copsboot.entities;

import java.io.Serializable;
import java.util.Objects;

public abstract class AbstractEntityId<T extends Serializable> implements Serializable {

	private T id;
	
	protected AbstractEntityId() {}
	
	protected AbstractEntityId(T id) {
		this.id = Objects.requireNonNull(id);
	}
	
	public T getId() {
		return this.id;
	}
	
	public String asString() {
		return this.id.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		return null;
	}
}
