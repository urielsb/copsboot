package com.uriel.copsboot.infrastructure.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.uriel.copsboot.infrastructure.SpringProfiles;
import com.uriel.copsboot.service.UserService;
import com.uriel.copsboot.user.Users;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(SpringProfiles.TEST)
public class OAuth2ServerConfigurationTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private UserService userService;
	
	@Test
	public void testGetAccessTokenAsOfficer() throws Exception {
		userService.createOfficer(Users.OFFICER_EMAIL, Users.OFFICER_PASSWORD);
		
		String clientId = "test-client-id";
		String clientSecret = "test-client-secret";
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "password");
		params.add("client_id", clientId);
		params.add("client_secret", clientSecret);
		params.add("username", Users.OFFICER_EMAIL);
		params.add("password", Users.OFFICER_PASSWORD);
		
		mvc.perform(MockMvcRequestBuilders.post("/oauth/token")
				.params(params)
				.with(SecurityMockMvcRequestPostProcessors.httpBasic(clientId, clientSecret))
				.accept("application/json;charset=UTF-8"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.jsonPath("access_token").isString())
			.andExpect(MockMvcResultMatchers.jsonPath("token_type").value("bearer"))
			.andExpect(MockMvcResultMatchers.jsonPath("refresh_token").isString())
			.andExpect(MockMvcResultMatchers.jsonPath("expires_in").isNumber())
			.andExpect(MockMvcResultMatchers.jsonPath("scope").value("mobile_app"));
	}

}
