/**
 * 
 */
package com.uriel.copsboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.uriel.copsboot.infrastructure.SpringProfiles;
import com.uriel.copsboot.service.UserService;

/**
 * @author Uriel Santoyo
 *
 */
@Component
@Profile(SpringProfiles.DEV)
public class DevelopmentDbInitializer implements ApplicationRunner {
	
	private final UserService userService;
	
	/**
	 * @param userService
	 */
	@Autowired
	public DevelopmentDbInitializer(UserService userService) {
		this.userService = userService;
	}

	/* (non-Javadoc)
	 * @see org.springframework.boot.ApplicationRunner#run(org.springframework.boot.ApplicationArguments)
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		createTestUsers();
	}
	
	private void createTestUsers() {
		userService.createOfficer("officer@example.com", "officer");
	}

}
