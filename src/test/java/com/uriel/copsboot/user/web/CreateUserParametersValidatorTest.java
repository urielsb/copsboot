package com.uriel.copsboot.user.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.uriel.copsboot.entities.User;
import com.uriel.copsboot.entities.UserId;
import com.uriel.copsboot.infrastructure.SpringProfiles;
import com.uriel.copsboot.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(SpringProfiles.TEST)
public class CreateUserParametersValidatorTest {

	@MockBean
	private UserService userService;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private ValidatorFactory factory;
	
	@Test
	public void invalidIfAlreadyUserWithGivenEmail() {
		String email = "wim.deblauwe@example.com";
		when(userService.findUserByEmail(email))
			.thenReturn(Optional.of(
					User.createOfficer(new UserId(UUID.randomUUID()), 
							email, 
							encoder.encode("testing1234"))));
		Validator validator = factory.getValidator();
		CreateOfficerParameters parameters = new CreateOfficerParameters();
		parameters.setEmail(email);
		parameters.setPassword("my-secret-pass");
		Set<ConstraintViolation<CreateOfficerParameters>> violationSet = validator.validate(parameters);
		assertThat(violationSet).isNotEmpty();
	}
	
	@Test
	public void validIfNoUsetWithGivenEmail() {
		String email = "wim.deblauwe@example.com";
		when(userService.findUserByEmail(email))
			.thenReturn(Optional.empty());
		
		Validator validator = factory.getValidator();
		CreateOfficerParameters parameters = new CreateOfficerParameters();
		parameters.setEmail(email);
		parameters.setPassword("my-secret-pass");
		Set<ConstraintViolation<CreateOfficerParameters>> violationSet = validator.validate(parameters);
		assertThat(violationSet).isEmpty();
	}

}
