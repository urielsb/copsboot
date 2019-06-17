/**
 * 
 */
package com.uriel.copsboot.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.uriel.copsboot.entities.User;
import com.uriel.copsboot.entities.UserId;
import com.uriel.copsboot.entities.UserRole;

/**
 * @author Uriel Santoyo
 *
 */
public class ApplicationUserDetails extends org.springframework.security.core.userdetails.User {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1L;

	private static final String ROLE_PREFIX = "ROLE_";
	
	private final UserId userId;
	
	public ApplicationUserDetails(User user) {
		super(user.getEmail(), user.getPassword(), createAuthorities(user.getRoles()) );
		this.userId = user.getId();
	}
	
	public UserId getUserId() {
		return userId;
	}
	
	private static Collection<SimpleGrantedAuthority> createAuthorities(Set<UserRole> roles) {
		return roles.stream()
				.map(userRole -> new SimpleGrantedAuthority(ROLE_PREFIX.concat(userRole.name() )) )
				.collect(Collectors.toSet());
	}
	
}
