/**
 * 
 */
package com.uriel.copsboot.security;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.uriel.copsboot.repositories.UserRepository;
import com.uriel.copsboot.user.Users;

/**
 * @author Uriel Santoyo
 *
 */
public class ApplicationUserDetailsServiceTest {

	@Test
	public void givenExistingUsername_whenLoading_userIsReturned() {
		UserRepository userRepository = Mockito.mock(UserRepository.class);
		ApplicationUserDetailService service =  new ApplicationUserDetailService(userRepository);
		Mockito.when(userRepository.findByEmailIgnoreCase(Users.OFFICER_EMAIL))
			.thenReturn(Optional.of(Users.officer()));
		UserDetails userDetails = service.loadUserByUsername(Users.OFFICER_EMAIL);

		Assertions.assertThat(userDetails).isNotNull();
		Assertions.assertThat(userDetails.getUsername()).isEqualTo(Users.OFFICER_EMAIL);
		Assertions.assertThat(userDetails.getAuthorities())
			.extracting(GrantedAuthority::getAuthority)
			.contains("ROLE_OFFICER");
		Assertions.assertThat(userDetails)
			.isInstanceOfSatisfying(ApplicationUserDetails.class, applicationUserDetails -> {
				Assertions.assertThat(applicationUserDetails.getUserId()).isEqualTo(Users.officer().getId());
			});
	}
	
	@Test(expected = UsernameNotFoundException.class)
	public void givenNotExistingUsername_whenLoadingUser_exceptionThrown() {
		UserRepository respository = Mockito.mock(UserRepository.class);
		ApplicationUserDetailService service = new ApplicationUserDetailService(respository);
		Mockito.when(respository.findByEmailIgnoreCase(ArgumentMatchers.anyString()))
			.thenReturn(Optional.empty());
		service.loadUserByUsername("i@donotexist.com");
	}

}
