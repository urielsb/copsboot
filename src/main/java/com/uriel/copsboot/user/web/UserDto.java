/**
 * 
 */
package com.uriel.copsboot.user.web;

import java.util.Set;

import com.uriel.copsboot.entities.User;
import com.uriel.copsboot.entities.UserId;
import com.uriel.copsboot.entities.UserRole;

import lombok.Value;

/**
 * @author Uriel Santoyo
 *
 */
@Value
public class UserDto {

	private final UserId id;
	private final String email;
	private final Set<UserRole> roles;
	
	public static UserDto fromUser(User user) {
		return new UserDto(user.getId(), user.getEmail(), user.getRoles());
	}
}
