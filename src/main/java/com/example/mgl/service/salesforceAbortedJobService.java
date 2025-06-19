package com.example.mgl.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.http.*;

@Service
public class salesforceAbortedJobService {
	
	private final HttpClient client = HttpClient.newHttpClient();

	@Value("${salesforce.instance-url}")
	private String instanceUrl;

	public JsonNode abortJob(String jobId, String accessToken) throws Exception {
		String url = instanceUrl+ "/services/data/v59.0/jobs/ingest/" + jobId;

// ***************************************************************************************
/*
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, String> body = new HashMap<>();
		body.put("state", "Aborted");

		HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PATCH, entity, String.class);

		return objectMapper.readTree(response.getBody());
		
*/
// *****************************************************************************************
		Map<String, String> body = new HashMap<>();
		body.put("state", "Aborted");
		
		ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(body);
		
		HttpRequest req = HttpRequest.newBuilder()
	            .uri(URI.create(url))
//	            .header("Accept", "application/json")
	            .header("Authorization", "Bearer " + accessToken)
	            .header("Content-Type", "application/json")
	            .method("PATCH", BodyPublishers.ofString(json))
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