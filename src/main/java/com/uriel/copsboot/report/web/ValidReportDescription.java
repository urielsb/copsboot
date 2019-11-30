/**
 * 
 */
package com.uriel.copsboot.report.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author Uriel Santoyo
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ReportDescriptionValidator.class})
public @interface ValidReportDescription {

	String message() default "Invalid report description";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}
