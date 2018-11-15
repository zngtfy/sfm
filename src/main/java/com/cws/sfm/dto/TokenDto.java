package com.cws.sfm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author ToanNguyen 2018-Nov-15 (verified)
 *
 */
public class TokenDto {
	// region -- Fields --

	@JsonProperty(value = "access_token")
	private String accessToken;

	@JsonProperty(value = "instance_url")
	private String instanceUrl;

	@JsonProperty(value = "id")
	private String id;

	@JsonProperty(value = "token_type")
	private String tokenType;

	@JsonProperty(value = "issued_at")
	private String issuedAt;

	@JsonProperty(value = "signature")
	private String signature;

	// end

	// region -- Get set --

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getInstanceUrl() {
		return instanceUrl;
	}

	public void setInstanceUrl(String instanceUrl) {
		this.instanceUrl = instanceUrl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getIssuedAt() {
		return issuedAt;
	}

	public void setIssuedAt(String issuedAt) {
		this.issuedAt = issuedAt;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	// end

	// region -- Methods --

	/**
	 * Initialize
	 */
	public TokenDto() {
		accessToken = "";
		instanceUrl = "";
		id = "";
		tokenType = "";
		issuedAt = "";
		signature = "";
	}

	// end
}