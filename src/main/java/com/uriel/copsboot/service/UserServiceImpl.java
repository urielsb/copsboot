/**
 * 
 */
package com.uriel.copsboot.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uriel.copsboot.entities.User;
import com.uriel.copsboot.entities.UserId;
import com.uriel.copsboot.repositories.UserRepository;

/**
 * @author Uriel Santoyo
 *
 */
@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository repository;
	private final PasswordEncoder encoder;
	
	/**
	 * @param repository
	 * @param encoder
	 */
	@Autowired
	public UserServiceImpl(UserRepository repository, PasswordEncoder encoder) {
		this.repository = repository;
		this.encoder = encoder;
	}

	/* (non-Javadoc)
	 * @see com.uriel.copsboot.service.UserService#createOfficer(java.lang.String, java.lang.String)
	 */
	@Override
	public User createOfficer(String email, String password) {
		User user = User.createOfficer(repository.nextId(), email, encoder.encode(password));
		return repository.save(user);
	}

	@Override
	public Optional<User> getUser(UserId id) {
		return repository.findById(id.getId());
	}

	@Override
	public Optional<User> findUserByEmail(String email) {
		return repository.findByEmailIgnoreCase(email);
	}

}
