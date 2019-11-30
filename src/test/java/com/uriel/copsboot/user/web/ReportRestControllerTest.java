package com.uriel.copsboot.user.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

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

import com.example.copsboot.infrastructure.security.StubUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uriel.copsboot.entities.Report;
import com.uriel.copsboot.entities.ReportId;
import com.uriel.copsboot.infrastructure.SpringProfiles;
import com.uriel.copsboot.infrastructure.security.OAuth2ServerConfiguration;
import com.uriel.copsboot.infrastructure.security.SecurityConfiguration;
import com.uriel.copsboot.report.web.CreateReportParameters;
import com.uriel.copsboot.report.web.ReportRestController;
import com.uriel.copsboot.service.ReportService;
import com.uriel.copsboot.user.Users;

@RunWith(SpringRunner.class)
@WebMvcTest(ReportRestController.class)
@ActiveProfiles(SpringProfiles.TEST)
public class ReportRestControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private ReportService service;
	
	@Test
	public void officerIsAbleToPostAReport() throws Exception {
		String accessToken = SecurityHelperForMockMvc.obtainAccessToken(mvc, Users.OFFICER_EMAIL, Users.OFFICER_PASSWORD);
		ZonedDateTime dateTime = ZonedDateTime.parse("2018-04-11T22:59:03.189+02:00");
		String description = "This is a test report description suspect";
		CreateReportParameters parameters = new CreateReportParameters(dateTime, description, true, 2);
		
		when(service.createReport(Mockito.eq(Users.officer().getId()), Mockito.any(ZonedDateTime.class), Mockito.eq(description)))
			.thenReturn(new Report(new ReportId(UUID.randomUUID()), Users.officer(), dateTime, description));
		
		mvc.perform(post("/api/reports")
				.header(SecurityHelperForMockMvc.HEADER_AUTHORIZATION, SecurityHelperForMockMvc.bearer(accessToken))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(parameters)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("id").exists())
			.andExpect(jsonPath("reporter").value(Users.OFFICER_EMAIL))
			.andExpect(jsonPath("dateTime").value("2018-04-11T22:59:03.189+02:00"))
			.andExpect(jsonPath("description").value(description));
			
	}
	
	@Test
	public void givenEmptyString_notValid() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		
		CreateReportParameters parameters = new CreateReportParameters(ZonedDateTime.now(), "", true, 0);
		Set<ConstraintViolation<CreateReportParameters>> validationSet = validator.validate(parameters);
		assertThat(validationSet).isNotEmpty();
	}
	
	@Test
	public void givenTrafficIncidentButInvolvedCarsZero_invalid() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		CreateReportParameters parameters = new CreateReportParameters(ZonedDateTime.now(), 
				"The suspect was wearing a black hat", true, 0);
		Set<ConstraintViolation<CreateReportParameters>> violationSet = validator.validate(parameters);
		assertThat(violationSet).isNotEmpty();
	}	
	
	@Test
	public void givenTrafficIncident_involvedCarsMustBePositive() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		CreateReportParameters parameters = new CreateReportParameters(ZonedDateTime.now(), 
				"The suspect was wearing a black hat", true, 2);
		Set<ConstraintViolation<CreateReportParameters>> validationSet = validator.validate(parameters);
		assertThat(validationSet).isEmpty();
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
