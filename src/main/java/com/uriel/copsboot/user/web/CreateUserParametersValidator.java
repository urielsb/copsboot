package com.uriel.copsboot.user.web;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.uriel.copsboot.service.UserService;

public class CreateUserParametersValidator implements ConstraintValidator<ValidCreateUserParameters, CreateOfficerParameters> {
	
	private final UserService userService;
	
	@Autowired
	public CreateUserParametersValidator(UserService userService) {
		this.userService = userService;
	}

	@Override
	public boolean isValid(CreateOfficerParameters value, ConstraintValidatorContext context) {
		boolean result = true;
		if(this.userService.findUserByEmail(value.getEmail()).isPresent()) {
			context.buildConstraintViolationWithTemplate("There is already a user with the given email address")
				.addPropertyNode("email").addConstraintViolation();
			
			result = false;
		}
		return result;
	}

}
