/**
 * 
 */
package com.uriel.copsboot.repositories;

import java.util.UUID;

import com.uriel.copsboot.entities.UserId;

/**
 * @author Uriel Santoyo
 *
 */
public class UserRepositoryImpl implements UserRepositoryCustom {
	
	/* (non-Javadoc)
	 * @see com.uriel.copsboot.repositories.UserRepositoryCustom#nextId()
	 */
	@Override
	public UserId nextId() {
		return new UserId(UUID.randomUUID());
	}

}
