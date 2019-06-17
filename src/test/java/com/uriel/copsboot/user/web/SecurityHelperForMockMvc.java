/**
 * 
 */
package com.uriel.copsboot.user.web;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author Uriel Santoyo
 *
 */
public class SecurityHelperForMockMvc {
	
	public static final String HEADER_AUTHORIZATION = "Authorization";

	private static final String UNIT_TEST_CLIENT_ID = "test-client-id";
	private static final String UNIT_TEST_CLIENT_SECRET = "test-client-secret";
	
	public static String obtainAccessToken(MockMvc mvc, String username, String password) throws Exception {
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "password");
		params.add("client_id", UNIT_TEST_CLIENT_ID);
		params.add("client_secret", UNIT_TEST_CLIENT_SECRET);
		params.add("username", username);
		params.add("password", password);
		
		ResultActions result = mvc.perform(MockMvcRequestBuilders.post("/oauth/token")
					.params(params)
					.with(SecurityMockMvcRequestPostProcessors.httpBasic(UNIT_TEST_CLIENT_ID, UNIT_TEST_CLIENT_SECRET))
					.accept("application/json;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content()
				.contentType("application/json;charset=UTF-8"));
		
		String resultString = result.andReturn().getResponse().getContentAsString();
		JacksonJsonParser jsonParser = new JacksonJsonParser();
		
		return jsonParser.parseMap(resultString).get("access_token").toString();
	}
	
	public static String bearer(String accessToken) {
		return "Bearer ".concat(accessToken);
	}
	
}
