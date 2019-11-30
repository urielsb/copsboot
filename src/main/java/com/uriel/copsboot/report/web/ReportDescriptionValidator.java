/**
 * 
 */
package com.uriel.copsboot.report.web;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author urielsb
 *
 */
public class ReportDescriptionValidator implements ConstraintValidator<ValidReportDescription, String> {

	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value.toLowerCase().contains("suspect");
	}

}
