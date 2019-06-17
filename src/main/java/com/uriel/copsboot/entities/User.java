/**
 * 
 */
package com.uriel.copsboot.entities;

import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.google.common.collect.Sets;

/**
 * @author Uriel Santoyo
 *
 */
@Entity
@Table(name="copsboot_user")
public class User extends AbstractEntity<UserId> {

	private String email;
	private String password;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	@NotNull
	private Set<UserRole> roles;
	
	/**
	 * Constructor with no arguments required by hibernate
	 */
	protected User() {}
	
	public User(UserId id, String email, String password, Set<UserRole> roles) {
		super(id);
		this.email = email;
		this.password = password;
		this.roles = roles;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}
	
	public static User createOfficer(UserId userId, String email, String password) {
		return new User(userId, email, password, Sets.newHashSet(UserRole.OFFICER));
	}
	
	public static User createCaptain(UserId userId, String email, String password) {
		return new User(userId, email, password, Sets.newHashSet(UserRole.CAPTAIN));
	}
	
}
