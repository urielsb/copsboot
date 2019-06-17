package com.uriel.copsboot.user.web;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.removeMatchingHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.copsboot.infrastructure.security.StubUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uriel.copsboot.infrastructure.SpringProfiles;
import com.uriel.copsboot.infrastructure.security.OAuth2ServerConfiguration;
import com.uriel.copsboot.infrastructure.security.SecurityConfiguration;
import com.uriel.copsboot.service.UserService;
import com.uriel.copsboot.user.Users;

@RunWith(SpringRunner.class)
@WebMvcTest(UserRestController.class)
@ActiveProfiles(SpringProfiles.TEST)
public class UserRestControllerDocumentation {
	
	@Rule
	public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private UserService service;

	@Autowired
	private WebApplicationContext context;
	private RestDocumentationResultHandler resultHandler;

	@Before
	public void setUp() {
		resultHandler = document("{method-name}", preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint(), removeMatchingHeaders("X.*", "Pragma", "Expires")));
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity())
				.apply(documentationConfiguration(restDocumentation)).alwaysDo(resultHandler).build();
	}

	@Test
	public void ownUserDetailsWhenNotLoggedInExample() throws Exception {
		mvc.perform(get("/api/users/me")).andExpect(status().isUnauthorized());
	}

	@Test
	public void authenticatedOfficerDetailsExample() throws Exception {
		String accessToken = SecurityHelperForMockMvc.obtainAccessToken(mvc, Users.OFFICER_EMAIL,
				Users.OFFICER_PASSWORD);

		when(service.getUser(Users.officer().getId())).thenReturn(Optional.of(Users.officer()));

		mvc.perform(get("/api/users/me")
				.header(SecurityHelperForMockMvc.HEADER_AUTHORIZATION, SecurityHelperForMockMvc.bearer(accessToken)))
				.andExpect(status().isOk())
				.andDo(resultHandler
						.document(responseFields(fieldWithPath("id").description("The unique id of the user."),
								fieldWithPath("email").description("The email address of the user."),
								fieldWithPath("roles").description("The security roles of the user."))));
	}

	@Test
	public void createOfficerExample() throws Exception {
		String email = "wim.deblauwe@example.com";
		String password = "my-super-secret-pwd";

		CreateOfficerParameters parameters = new CreateOfficerParameters(); // <1>
		parameters.setEmail(email);
		parameters.setPassword(password);

		when(service.createOfficer(email, password)).thenReturn(Users.newOfficer(email, password)); // <2>

		mvc.perform(post("/api/users") // <3>
				.contentType(MediaType.APPLICATION_JSON_UTF8).content(objectMapper.writeValueAsString(parameters))) // <4>
				.andExpect(status().isCreated()) // <5>
				.andDo(resultHandler.document(requestFields( // <6>
						fieldWithPath("email").description("The email address of the user to be created."),
						fieldWithPath("password").description("The password for the new user.")),
						responseFields( // <7>
								fieldWithPath("id").description("The unique id of the user."),
								fieldWithPath("email").description("The email address of the user."),
								fieldWithPath("roles").description("The security roles of the user."))));
	}

	@TestConfiguration
	@Import(OAuth2ServerConfiguration.class)
	static class TestConfig {
		@Bean
		public UserDetailsService userDetailsService() {
			return new StubUserDetailsService();
		}

		@Bean
		public SecurityConfiguration securityConfiguration() {
			return new SecurityConfiguration();
		}

	}
}