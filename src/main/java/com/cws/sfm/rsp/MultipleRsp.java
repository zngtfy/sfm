package com.cws.sfm.rsp;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author ToanNguyen 2018-Nov-15 (verified)
 *
 */
public class MultipleRsp extends BaseRsp {
	// region -- Fields --

	@JsonProperty(value = "result")
	private Map<String, Object> result;

	// end

	// region -- Get set --

	public Map<String, Object> getResult() {
		return result;
	}

	public void setResult(Map<String, Object> result) {
		this.result = result;
	}

	// end

	// region -- Methods --

	/**
	 * Initialize
	 */
	public MultipleRsp() {
	}

	/**
	 * Initialize
	 * 
	 * @param status
	 * @param message
	 * @param result
	 */
	public MultipleRsp(String status, String message, Map<String, Object> result) {
		super(status, message);

		this.result = result;
	}

	// end
}