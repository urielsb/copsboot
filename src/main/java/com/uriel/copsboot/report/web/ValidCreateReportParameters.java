package com.uriel.copsboot.report.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CreateReportParametersValidator.class})
public @interface ValidCreateReportParameters {

	String message() default "Invalid report";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}
