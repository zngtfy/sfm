package com.cws.sfm.req;

import com.cws.sfm.dto.AuthenticationDto;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author ToanNguyen 2018-Nov-15 (verified)
 *
 */
public class AuthenticationReq {
	// region -- Fields --

	@JsonProperty(value = "source")
	private AuthenticationDto source;

	@JsonProperty(value = "destination")
	private AuthenticationDto destination;

	// end

	// region -- Get set --

	public AuthenticationDto getSource() {
		return source;
	}

	public void setSource(AuthenticationDto source) {
		this.source = source;
	}

	public AuthenticationDto getDestination() {
		return destination;
	}

	public void setDestination(AuthenticationDto destination) {
		this.destination = destination;
	}

	// end

	// region -- Methods --

	/**
	 * Initialize
	 */
	public AuthenticationReq() {
		source = new AuthenticationDto();
		destination = new AuthenticationDto();
	}

	// end
}