/**
 * 
 */
package com.uriel.copsboot.user.web;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.copsboot.infrastructure.security.StubUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uriel.copsboot.infrastructure.SpringProfiles;
import com.uriel.copsboot.infrastructure.security.OAuth2ServerConfiguration;
import com.uriel.copsboot.infrastructure.security.SecurityConfiguration;
import com.uriel.copsboot.service.UserService;
import com.uriel.copsboot.user.Users;

/**
 * @author Uriel Santoyo
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserRestController.class)
@ActiveProfiles(SpringProfiles.TEST)
public class UserRestControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private UserService service;

	@Test
	public void givenNotAuthenticated_whenAskingMyDetails_forbidden() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/api/users/me"))
			.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}
	
	@Test
	public void givenAuthenticatedAsOfficer_whenAskingMyDetails_detailsReturned() throws Exception {
		String accessToken = SecurityHelperForMockMvc.obtainAccessToken(mvc, Users.OFFICER_EMAIL, Users.OFFICER_PASSWORD);
		Mockito.when(service.getUser(Users.officer().getId())).thenReturn(Optional.of(Users.officer()));
		
		mvc.perform(MockMvcRequestBuilders.get("/api/users/me")
				.header(SecurityHelperForMockMvc.HEADER_AUTHORIZATION, SecurityHelperForMockMvc.bearer(accessToken)))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("id").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("email").value(Users.OFFICER_EMAIL))
			.andExpect(MockMvcResultMatchers.jsonPath("roles").isArray())
			.andExpect(MockMvcResultMatchers.jsonPath("roles[0]").value("OFFICER"));
	}
	
	@Test
	public void testCreateOfficer() throws Exception {
		String email = "uriel.sb@mail.com";
		String password = "my-password";
		
		CreateOfficerParameters params = new CreateOfficerParameters();
		params.setEmail(email);
		params.setPassword(password);
		
		Mockito.when(service.createOfficer(email, password)).thenReturn(Users.newOfficer(email, password));
		
		mvc.perform(MockMvcRequestBuilders.post("/api/users")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(params)) )
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.jsonPath("id").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("email").value(email))
			.andExpect(MockMvcResultMatchers.jsonPath("roles").isArray())
			.andExpect(MockMvcResultMatchers.jsonPath("roles[0]").value("OFFICER"));
	}
	
	@Test
	public void testCreateOfficerIfPasswordIsTooShot() throws Exception{
		String email = "wim.deblawe@example.com";
		String password = "pwd";
		
		CreateOfficerParameters parameters = new CreateOfficerParameters();
		parameters.setEmail(email);
		parameters.setPassword(password);
		
		mvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(parameters)))
			.andExpect(status().isBadRequest())
			.andDo(print());
		
		verify(service, never()).createOfficer(email, password);
	}
	
	@TestConfiguration
	@Import(OAuth2ServerConfiguration.class)
	static class TestConfig {
		
		@Bean
		public UserDetailsService userDetailsService() {
			return new StubUserDetailsService();
		}
		
		/*@Bean
		public TokenStore tokenStore() {
			return new InMemoryTokenStore();
		}*/
		
		@Bean
		public SecurityConfiguration securityConfiguration() {
			return new SecurityConfiguration();
		}
		
	}

}
