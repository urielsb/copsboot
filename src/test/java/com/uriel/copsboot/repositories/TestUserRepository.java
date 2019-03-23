package com.uriel.copsboot.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.uriel.copsboot.entities.User;
import com.uriel.copsboot.entities.UserRole;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TestUserRepository {
	
	@Autowired
	private UserRepository userRepository;

	@Test
	public void testStoreUser() {
		Set<UserRole> roles = new HashSet<>();
		roles.add(UserRole.OFFICER);
		
		User user = userRepository.save(new User(UUID.randomUUID(), 
				"test_user@mail.com", 
				"my-secret-pwd", 
				roles));
		
		assertThat(user).isNotNull();
		assertThat(userRepository.count()).isEqualTo(1L);
	}

}
