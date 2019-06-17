/**
 * 
 */
package com.example.copsboot.infrastructure.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.uriel.copsboot.security.ApplicationUserDetails;
import com.uriel.copsboot.user.Users;

/**
 * @author Uriel Santoyo
 *
 */
public class StubUserDetailsService implements UserDetailsService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetailsService#
	 * loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		switch (username) {
			case Users.OFFICER_EMAIL:
				return new ApplicationUserDetails(Users.officer());
			case Users.CAPTAIN_EMAIL:
				return new ApplicationUserDetails(Users.captain());
			default:
				throw new UsernameNotFoundException(username);
		}
	}

}
