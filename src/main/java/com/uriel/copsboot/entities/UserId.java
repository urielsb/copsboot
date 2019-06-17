/**
 * 
 */
package com.uriel.copsboot.entities;

import java.util.UUID;

/**
 * @author Uriel Santoyo
 *
 */
public class UserId extends AbstractEntityId<UUID> {

	protected UserId() {}
	
	public UserId(UUID id) {
		super(id);
	}
}
