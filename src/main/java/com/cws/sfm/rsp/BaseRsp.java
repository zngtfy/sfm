package com.cws.sfm.rsp;

import com.cws.sfm.common.Const;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author ToanNguyen 2018-Nov-15 (verified)
 *
 */
public class BaseRsp {
	// region -- Fields --

	@JsonProperty(value = "status")
	private String status;

	@JsonProperty(value = "message")
	private String message;

	// end

	// region -- Get set --

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	// end

	// region -- Methods --

	/**
	 * Initialize
	 */
	public BaseRsp() {
		this.status = Const.HTTP.STATUS_SUCCESS;
		this.message = "";
	}

	/**
	 * Initialize
	 * 
	 * @param status
	 * @param message
	 */
	public BaseRsp(String status, String message) {
		this.status = status;
		this.message = message;
	}

	/**
	 * Set error
	 * 
	 * @param message
	 */
	public void setError(String message) {
		this.status = Const.HTTP.STATUS_ERROR;
		this.message = message;
	}

	// end
}