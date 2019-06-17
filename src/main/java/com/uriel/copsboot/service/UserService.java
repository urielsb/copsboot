/**
 * 
 */
package com.uriel.copsboot.service;

import java.util.Optional;

import com.uriel.copsboot.entities.User;
import com.uriel.copsboot.entities.UserId;

/**
 * @author Uriel Santoyo
 *
 */
public interface UserService {

	User createOfficer(String email, String password);
	
	Optional<User> getUser(UserId id);
}
