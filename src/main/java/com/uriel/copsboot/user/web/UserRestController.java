/**
 * 
 */
package com.uriel.copsboot.user.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.uriel.copsboot.entities.User;
import com.uriel.copsboot.security.ApplicationUserDetails;
import com.uriel.copsboot.service.UserService;
import com.uriel.copsboot.user.UserNotFountException;

/**
 * @author Uriel Santoyo
 *
 */
@RestController
@RequestMapping("/api/users")
public class UserRestController {

	private final UserService userService;
	
	@Autowired
	public UserRestController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/me")
	public UserDto currentUser(@AuthenticationPrincipal ApplicationUserDetails userDetails) {
		User currentUser = userService.getUser(userDetails.getUserId())
				.orElseThrow(() -> new UserNotFountException(userDetails.getUserId()) );
		return UserDto.fromUser(currentUser);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserDto createOfficer(@Valid @RequestBody CreateOfficerParameters params) {
		User officer = userService.createOfficer(params.getEmail(), params.getPassword());
		return UserDto.fromUser(officer);
	}
	
}
