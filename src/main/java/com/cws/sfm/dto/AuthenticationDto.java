package com.cws.sfm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author ToanNguyen 2018-Nov-15 (verified)
 *
 */
public class AuthenticationDto {
	// region -- Fields --

	@JsonProperty(value = "clientId")
	private String clientId;

	@JsonProperty(value = "clientSecret")
	private String clientSecret;

	@JsonProperty(value = "userName")
	private String userName;

	@JsonProperty(value = "password")
	private String password;

	@JsonProperty(value = "url")
	private String url;

	// end

	// region -- Get set --

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	// end

	// region -- Methods --

	/**
	 * Initialize
	 */
	public AuthenticationDto() {
		clientId = "";
		clientSecret = "";
		userName = "";
		password = "";
		url = "";
	}

	// end
}