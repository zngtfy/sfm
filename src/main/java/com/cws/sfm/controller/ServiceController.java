package com.cws.sfm.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cws.sfm.dto.AuthenticationDto;
import com.cws.sfm.dto.TokenDto;
import com.cws.sfm.req.AuthenticationReq;
import com.cws.sfm.rsp.SingleRsp;
import com.fasterxml.jackson.databind.ObjectMapper;

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

	private TokenDto getToken(AuthenticationDto o) {
		TokenDto res = new TokenDto();

		try {
			String url = o.getUrl();
			String clientId = o.getClientId();
			String clientSecret = o.getClientSecret();
			String userName = o.getUserName();
			String password = o.getPassword();

			RestTemplate rst = new RestTemplate();

			HttpHeaders hdr = new HttpHeaders();
			hdr.setContentType(MediaType.MULTIPART_FORM_DATA);

			LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("grant_type", "password");
			map.add("client_id", clientId);
			map.add("client_secret", clientSecret);
			map.add("username", userName);
			map.add("password", password);

			HttpEntity<LinkedMultiValueMap<String, Object>> reqEntity;
			reqEntity = new HttpEntity<>(map, hdr);

			ResponseEntity<String> rsp;
			rsp = rst.exchange(url, HttpMethod.POST, reqEntity, String.class);

			String s = rsp.getBody();
			ObjectMapper mapper = new ObjectMapper();
			res = mapper.readValue(s, TokenDto.class);
		} catch (Exception ex) {
			ex.printStackTrace();
			_log.log(Level.SEVERE, ex.getMessage(), ex);
		}

		return res;
	}

	@PostMapping("/upload")
	public ResponseEntity<?> upload(@RequestBody AuthenticationReq req) {
		SingleRsp res = new SingleRsp();

		try {
			AuthenticationDto src = req.getSource();
			// AuthenticationDto des = req.getDestination();

			TokenDto t = getToken(src);
			String token = t.getAccessToken();
			String url = t.getInstanceUrl();
			url += "/services/data/v44.0/ui-api/object-info/Account";

			RestTemplate rst = new RestTemplate();

			HttpHeaders hdr = new HttpHeaders();
			hdr.set("Authorization", "Bearer " + token);

			LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			HttpEntity<LinkedMultiValueMap<String, Object>> reqEntity;
			reqEntity = new HttpEntity<>(map, hdr);

			ResponseEntity<String> rsp;
			rsp = rst.exchange(url, HttpMethod.GET, reqEntity, String.class);

			String s = rsp.getBody();
			res.setResult(s);
		} catch (Exception ex) {
			ex.printStackTrace();
			_log.log(Level.SEVERE, ex.getMessage(), ex);
		}

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	// end
}