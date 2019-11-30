package com.uriel.copsboot.user.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CreateUserParametersValidator.class})
public @interface ValidCreateUserParameters {

	String message() default "Invalid User";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}
