/**
 * 
 */
package com.uriel.copsboot.infrastructure.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Uriel Santoyo
 *
 */
@Component
@ConfigurationProperties(prefix = "copsboot-security")
public class SecurityConfiguration {

	private String mobileAppClientId;
	private String mobileAppClientSecret;
	
	/**
	 * @return the mobileAppClientId
	 */
	public String getMobileAppClientId() {
		return mobileAppClientId;
	}
	
	/**
	 * @param mobileAppClientId the mobileAppClientId to set
	 */
	public void setMobileAppClientId(String mobileAppClientId) {
		this.mobileAppClientId = mobileAppClientId;
	}
	
	/**
	 * @return the mobileAppClientSecret
	 */
	public String getMobileAppClientSecret() {
		return mobileAppClientSecret;
	}
	
	/**
	 * @param mobileAppClientSecret the mobileAppClientSecret to set
	 */
	public void setMobileAppClientSecret(String mobileAppClientSecret) {
		this.mobileAppClientSecret = mobileAppClientSecret;
	}
	
	
}
