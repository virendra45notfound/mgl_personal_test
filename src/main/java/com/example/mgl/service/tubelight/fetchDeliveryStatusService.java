package com.example.mgl.service.tubelight;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Service
public class fetchDeliveryStatusService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${tubelight.delivery-status-url}")		private String tubeDeliveryStatusUrl;
	@Value("${tubelight.username}")	private String tubeUserName;
	@Value("${tubelight.password}")		private String tubePassword;
	
	public String fetchDeliveryStatus(String messageId) throws IOException, InterruptedException {
		String url = UriComponentsBuilder.fromUriString(tubeDeliveryStatusUrl)
							.queryParam("username", tubeUserName)
							.queryParam("password", tubePassword)
							.queryParam("messageid", messageId)
							.toUriString();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAccept(Collections.singletonList(MediaType.TEXT_PLAIN));

		HttpEntity<String> entity = new HttpEntity<>(headers);

		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
			return response.getBody();
		} catch (Exception e) {
			return "Failed to fetch Balance Details: " + e.getMessage();
		}

	}


}
