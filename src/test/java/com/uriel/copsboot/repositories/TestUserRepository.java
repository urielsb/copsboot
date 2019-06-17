package com.uriel.copsboot.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.uriel.copsboot.entities.User;
import com.uriel.copsboot.entities.UserRole;
import com.uriel.copsboot.user.Users;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TestUserRepository {
	
	@Autowired
	private UserRepository userRepository;

	@Test
	public void testStoreUser() {
		Set<UserRole> roles = new HashSet<>();
		roles.add(UserRole.OFFICER);
		
		User user = userRepository.save(new User(userRepository.nextId(), 
				"test_user@mail.com", 
				"my-secret-pwd", 
				roles));
		
		assertThat(user).isNotNull();
		assertThat(userRepository.count()).isEqualTo(1L);
	}
	
	@Test
	public void testFindByEmail() {
		User user = Users.newRandomOfficer();
		userRepository.save(user);
		Optional<User> optional = userRepository.findByEmailIgnoreCase(user.getEmail());
		
		assertThat(optional).isNotEmpty().contains(user);
	}
	
	@Test
	public void testFindByEmailIgnoringCase() {
		User user = Users.newRandomOfficer();
		userRepository.save(user);
		Optional<User> optional = userRepository.findByEmailIgnoreCase(user.getEmail().toUpperCase(Locale.US));
		
		assertThat(optional).isNotEmpty().contains(user);
	}
	
	@Test
	public void testFindByEmail_unknownEmail() {
		User user = Users.newRandomOfficer();
		userRepository.save(user);
		Optional<User> optional = userRepository.findByEmailIgnoreCase("will.not@find.me");
		
		assertThat(optional).isEmpty();
	}

}
