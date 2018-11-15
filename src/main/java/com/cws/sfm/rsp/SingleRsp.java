package com.cws.sfm.rsp;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author ToanNguyen 2018-Nov-15 (verified)
 *
 */
public class SingleRsp extends BaseRsp {
	// region -- Fields --

	@JsonProperty(value = "result")
	private Object result;

	// end

	// region -- Get set --

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	// end

	// region -- Methods --

	/**
	 * Initialize
	 */
	public SingleRsp() {
	}

	/**
	 * Initialize
	 * 
	 * @param status
	 * @param message
	 * @param result
	 */
	public SingleRsp(String status, String message, Object result) {
		super(status, message);

		this.result = result;
	}

	// end
}