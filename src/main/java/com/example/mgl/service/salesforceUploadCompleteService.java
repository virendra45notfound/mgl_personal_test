package com.example.mgl.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class salesforceUploadCompleteService {

	private final HttpClient client = HttpClient.newHttpClient();

	@Value("${salesforce.instance-url}")
	private String instanceUrl;


	public JsonNode markUploadCompleteInstance(String accessToken, String jobId) throws JsonProcessingException {
		String url1 = instanceUrl + "/services/data/v59.0/jobs/ingest/" + jobId;
		
		Map<String, String> body = new HashMap<>();
		body.put("state", "UploadComplete");
		
		ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(body);
		
		HttpRequest req = HttpRequest.newBuilder()
	            .uri(URI.create(url1))
	            .header("Accept", "application/json")
	            .header("Authorization", "Bearer " + accessToken)
	            .header("Content-Type", "application/json")
	            .method("PATCH", BodyPublishers.ofString(json))
	            .build();

		try {
			HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
			return new ObjectMapper().readTree(resp.body());
		} catch (Exception e) {
			throw new RuntimeException("Failed to complete upload for Instance : " + e.getMessage());
		}
	}

//	public JsonNode markUploadCompleteInstance2(String accessToken, String jobId) {
//		String url2 = instanceUrl + "/services/data/v59.0/jobs/ingest/" + jobId;
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//		headers.setBearerAuth(accessToken);
//
//		String body = "{\"state\":\"UploadComplete\"}";
//		HttpEntity<String> request = new HttpEntity<>(body, headers);
//
//		try {
//			ResponseEntity<String> response = restTemplate.exchange(url2, HttpMethod.PATCH, request, String.class);
//			return objectMapper.readTree(response.getBody());
//		} catch (Exception e) {
//			throw new RuntimeException("Failed to complete upload for Instance 2: " + e.getMessage());
//		}
//	}
}
