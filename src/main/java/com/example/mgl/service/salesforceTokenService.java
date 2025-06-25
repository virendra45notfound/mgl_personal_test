package com.example.mgl.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.example.mgl.model.requestData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.Collections;

@Service
public class salesforceTokenService {

	// Salesforce 
	@Value("${salesforce.grant-type}")		private String grantType;
	@Value("${salesforce.client-id}")			private String clientId;
	@Value("${salesforce.client-secret}")	private String clientSecret;
	@Value("${salesforce.username}")		private String username;
	@Value("${salesforce.password}")		private String password;
	@Value("${salesforce.token-url}")		private String tokenUrl;
	
	
//	private final RestTemplate restTemplate = new RestTemplate();
	private final HttpClient client = HttpClient.newHttpClient();

	public JsonNode getAccessToken() throws IOException, InterruptedException {
		return generateToken(grantType, clientId, clientSecret, username, password, tokenUrl);
	}

	private JsonNode generateToken(String grantType, String clientId, String clientSecret, String username,
			String password, String tokenUrl) throws IOException, InterruptedException {
		String url = UriComponentsBuilder.fromUriString(tokenUrl).queryParam("grant_type", grantType)
				.queryParam("client_id", clientId).queryParam("client_secret", clientSecret)
				.queryParam("username", username).queryParam("password", password).toUriString();

		HttpRequest req = HttpRequest.newBuilder()
	            .uri(URI.create(url))
	            .header("Accept", "application/json")
	            .POST(HttpRequest.BodyPublishers.noBody())
	            .build();
		try {
			
			HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
			return new ObjectMapper().readTree(resp.body());
		} catch(Exception e) {
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode response = mapper.createObjectNode();
			response.put("Status", "Failed");
			response.put("Message", e.getMessage());
			return response;
		}
		
	}
	
	
}
