/**
 * 
 */
package com.uriel.copsboot.user;

import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.uriel.copsboot.entities.User;
import com.uriel.copsboot.entities.UserId;

/**
 * @author Uriel Santoyo
 *
 */
public class Users {

	private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
	
	public static final String OFFICER_EMAIL = "officer@example.com";
	public static final String OFFICER_PASSWORD = "officer";
	public static final String CAPTAIN_EMAIL = "captain@example.com";
	public static final String CAPTAIN_PASSWORD = "captain";
	
	private static User CAPTAIN = User.createCaptain(newRandomId(), CAPTAIN_EMAIL, PASSWORD_ENCODER.encode(CAPTAIN_PASSWORD));
	private static User OFFICER = User.createOfficer(newRandomId(), OFFICER_EMAIL, PASSWORD_ENCODER.encode(OFFICER_PASSWORD));
	
			
	public static UserId newRandomId() {
		return new UserId(UUID.randomUUID());
	}
	
	public static User newRandomOfficer(UserId userId) {
		String uniqueId = userId.asString().substring(0, 5);
		return User.createOfficer(userId, String.format("user-%s@example.com", uniqueId), PASSWORD_ENCODER.encode("user"));
	}
	
	public static User newRandomOfficer() {
		return newRandomOfficer(newRandomId());
	}
	
	public static User newOfficer(String email, String password) {
		return User.createOfficer(new UserId(UUID.randomUUID()), email, password);
	}
	
	public static User officer() {
		return OFFICER;
	}
	
	public static User captain() {
		return CAPTAIN;
	}
}
