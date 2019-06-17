/**
 * 
 */
package com.uriel.copsboot.entities;

/**
 * @author Uriel Santoyo
 *
 */
public interface Entity<T extends EntityId> {

	T getId();
}
