/**
 * 
 */
package com.uriel.copsboot.entities;

import java.io.Serializable;

/**
 * @author Uriel Santoyo
 *
 */
public interface EntityId<T> extends Serializable {

	T getId();
	
	String asString();
}
