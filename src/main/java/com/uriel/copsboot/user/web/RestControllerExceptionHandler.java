/**
 * 
 */
package com.uriel.copsboot.user.web;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * @author Uriel Santoyo
 *
 */
@ControllerAdvice
public class RestControllerExceptionHandler {

	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, List<String>> handle(MethodArgumentNotValidException exception) {
		return error(exception
				.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(fieldError -> fieldError.getField() + " - " + fieldError.getDefaultMessage())
				.collect(Collectors.toList()) );
	}
	
	private Map<String, List<String>> error(List<String> errors) {
		return Collections.singletonMap("errors", errors);
	}
}
