/**
 * 
 */
package com.uriel.copsboot.user;

import org.springframework.web.bind.annotation.ResponseStatus;

import com.uriel.copsboot.entities.UserId;

import org.springframework.http.HttpStatus;

/**
 * @author Uriel Santoyo
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFountException extends RuntimeException {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1L;
	
	public UserNotFountException(UserId userId) {
		super(String.format("Could not find user with id %s", userId.asString()) );
	}

}
