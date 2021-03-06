package com.cws.sfm.controller;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;
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

import com.cws.sfm.common.ZFile;
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

	private String _path;

	private String _temp;

	private String _valueSet;

	private String _formula;

	// end

	// region -- Methods --

	/**
	 * Initialize
	 */
	public ServiceController() {
		_path = ZFile.getPath("/template/");
		_temp = ZFile.read(_path + "_temp.txt");
		_valueSet = ZFile.read(_path + "_valueSet.txt");
		_formula = ZFile.read(_path + "_formula.txt");
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

	private String getObject(AuthenticationDto o, String object) {
		String res = "";

		try {
			TokenDto t = getToken(o);

			String token = t.getAccessToken();
			String url = t.getInstanceUrl();
			url += "/services/data/v44.0/ui-api/object-info/" + object;

			RestTemplate rst = new RestTemplate();

			HttpHeaders hdr = new HttpHeaders();
			hdr.set("Authorization", "Bearer " + token);

			LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			HttpEntity<LinkedMultiValueMap<String, Object>> reqEntity;
			reqEntity = new HttpEntity<>(map, hdr);

			ResponseEntity<String> rsp;
			rsp = rst.exchange(url, HttpMethod.GET, reqEntity, String.class);

			res = rsp.getBody();
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
			AuthenticationDto src = req.getSource(); // demo
			AuthenticationDto des = req.getDestination(); // prod

			String object = req.getObject();
			String demo = getObject(src, object);
			// String prod = getObject(des, object);
			String t = "";

			// ZFile.write(_path + object + "-demo.json", demo);
			// ZFile.write(_path + object + "-prod.json", prod);

			JSONObject resobj = new JSONObject(demo);
			JSONObject to = new JSONObject(resobj.get("fields").toString());
			Iterator<?> keys = to.keys();

			while (keys.hasNext()) {
				String key = (String) keys.next();

				if (key.endsWith("__c")) {
					System.out.println(key);

					if (to.get(key) instanceof JSONObject) {
						JSONObject xx = new JSONObject(to.get(key).toString());
						String fullName = (String) xx.get("apiName");
						String label = (String) xx.get("label");
						String type = (String) xx.get("dataType");
						Integer length = (Integer) xx.get("length");
						Boolean calculated = (Boolean) xx.get("calculated");
						Integer precision = (Integer) xx.get("precision");
						Integer scale = (Integer) xx.get("scale");

						if ("String".equals(type)) {
							type = "Text";
						}

						if ("Boolean".equals(type)) {
							type = "Checkbox";
						}

						if ("Einstein_Article_3_Date__c".equals(fullName)) {
							continue;
						}
						if ("Einstein_Article_3_URL__c".equals(fullName)) {
							continue;
						}
						if ("Partner_Program_Level__c".equals(fullName)) {
							continue;
						}

						// Formula
						if ("Double".equals(type)) {
							continue;
						}

						if ("MultiPicklist".equals(type)) {
							continue;
						}

						if ("Reference".equals(type)) {
							continue;
						}

						if ("Account_Status__c".equals(fullName)) {
							continue;
						}

						if ("Goal_Attainment__c".equals(fullName)) {
							continue;
						}

						// Skip
						if ("jsImpacts__Data_com_does_not_auto_update__c".equals(fullName)) {
							continue;
						}
						if ("EDY_ORGCUSTOM__Old_Name__c".equals(fullName)) {
							continue;
						}
						if ("ShippingGeo__c".equals(fullName)) {
							continue;
						}
						if ("jsImpacts__Data_com_Matched__c".equals(fullName)) {
							continue;
						}
						if ("EDY_ORGCUSTOM__DB__c".equals(fullName)) {
							continue;
						}

						String t2 = _temp.replace("{fullName}", fullName);

						if ("Picklist".equals(type)) {
							t2 = t2.replace("<type>{type}</type>", _valueSet);
						}

						t2 = t2.replace("{label}", label);
						t2 = t2.replace("{type}", type);

						if ("Currency".equals(type) || "Percent".equals(type)) {
							t2 = t2.replace("{precision}", precision.toString());
							t2 = t2.replace("{scale}", scale.toString());
						} else {
							t2 = t2.replace("<precision>{precision}</precision>", "");
							t2 = t2.replace("<scale>{scale}</scale>", "");
						}

						if (calculated == true) {
							if ("Currency".equals(type) || "Percent".equals(type)) {
								t2 = t2.replace("{formula}", "0");
							} else if ("Text".equals(type)) {
								t2 = t2.replace("{formula}", "&quot;&quot;");
							}
						} else {
							if ("Text".equals(type)) {
								t2 = t2.replace("{length}", length.toString());
							}

							t2 = t2.replace("<formula>{formula}</formula>", "");
						}

						if ("Checkbox".equals(type)) {
							t2 = t2.replace("<length>{length}</length>", "<defaultValue>false</defaultValue>");
						} else {
							t2 = t2.replace("<length>{length}</length>", "");
						}

						t += t2;
					}
				}
			}

			ZFile.write(_path + object + ".html", t);
			res.setResult(t);
		} catch (Exception ex) {
			ex.printStackTrace();
			_log.log(Level.SEVERE, ex.getMessage(), ex);
		}

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PostMapping("/get-info")
	public ResponseEntity<?> getInfo(@RequestBody AuthenticationReq req) {
		SingleRsp res = new SingleRsp();

		try {
			AuthenticationDto src = req.getSource(); // demo

			String object = req.getObject();
			String demo = getObject(src, object);
			String t = "";
			ZFile.write(_path + object + "-demo.json", demo);

			JSONObject resobj = new JSONObject(demo);
			JSONObject to = new JSONObject(resobj.get("fields").toString());
			Iterator<?> keys = to.keys();
			int totalField = 0;
			int totalCustomField = 0;
			while (keys.hasNext()) {
				String key = (String) keys.next();
				String t1 = "";
				if (to.get(key) instanceof JSONObject) {
					JSONObject xx = new JSONObject(to.get(key).toString());
					String fullName = (String) xx.get("apiName");
					String label = (String) xx.get("label");
					String type = (String) xx.get("dataType");
					Integer length = (Integer) xx.get("length");
					Integer precision = (Integer) xx.get("precision");
					Integer scale = (Integer) xx.get("scale");
					Boolean calculated = (Boolean) xx.get("calculated");

					if ("String".equals(type)) {
						type = "Text";
					}
					if ("Boolean".equals(type)) {
						type = "Checkbox";
					}
					if ("Reference".equals(type)) {
						type = "Lookup";
					}

					t1 = fullName + "\t" + label + "\t";
					t1 += type + "\t" + length + "\t";
					t1 += precision + "\t" + scale + "\t";
					t1 += calculated + "\t";
				}

				if (key.endsWith("__c")) {
					totalCustomField++;
					t1 += "TRUE" + "\n";
				} else {
					t1 += "FALSE" + "\n";
				}

				t += t1;
				totalField++;
			}

			ZFile.write(_path + object + ".html", t);
			res.setResult(t);
		} catch (Exception ex) {
			ex.printStackTrace();
			_log.log(Level.SEVERE, ex.getMessage(), ex);
		}

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	// end
}