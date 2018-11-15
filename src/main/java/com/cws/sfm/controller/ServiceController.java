package com.cws.sfm.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cws.sfm.common.ZConfig;
import com.cws.sfm.req.AuthenticationReq;
import com.cws.sfm.rsp.SingleRsp;

/**
 * 
 * @author ToanNguyen 2018-Nov-15 (verified)
 *
 */
@RestController
@RequestMapping("/service")
public class ServiceController {
	// region -- Fields --

	private static final Logger _log = Logger.getLogger(ServiceController.class.getName());

	// end

	// region -- Methods --

	/**
	 * Initialize
	 */
	public ServiceController() {

	}

	@PostMapping("/upload")
	public ResponseEntity<?> upload(@RequestBody AuthenticationReq req) {
		SingleRsp res = new SingleRsp();

		try {

		} catch (Exception ex) {
			if (ZConfig._printTrace) {
				ex.printStackTrace();
			}
			if (ZConfig._writeLog) {
				_log.log(Level.SEVERE, ex.getMessage(), ex);
			}
		}

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	// end
}