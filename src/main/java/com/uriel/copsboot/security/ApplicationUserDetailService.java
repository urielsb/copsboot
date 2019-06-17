/**
 * 
 */
package com.uriel.copsboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.uriel.copsboot.entities.User;
import com.uriel.copsboot.repositories.UserRepository;

/**
 * @author Uriel Santoyo
 *
 */
@Service
public class ApplicationUserDetailService implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	@Autowired
	public ApplicationUserDetailService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmailIgnoreCase(username)
				.orElseThrow( () -> new UsernameNotFoundException(String.format("User with email %s could not be found", username)));
		
		return new ApplicationUserDetails(user);
	}

}
