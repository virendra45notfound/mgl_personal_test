package com.example.mgl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.mgl.model.requestData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class salesforceCreateDataService {
	private final HttpClient client = HttpClient.newHttpClient();

	@Value("${salesforce.instance-url}")
	private String instanceUrl;

	public JsonNode createJob2(String accessToken, String objectName, String externalIdFieldName) throws JsonProcessingException {
		String url = instanceUrl + "/services/data/v59.0/jobs/ingest";

		
		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("operation", "upsert");
		requestBody.put("object", objectName);
		requestBody.put("externalIdFieldName", externalIdFieldName);
		requestBody.put("contentType", "CSV");
		requestBody.put("lineEnding", "LF");
		
		ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(requestBody);

		HttpRequest req = HttpRequest.newBuilder()
	            .uri(URI.create(url))
	            .header("Accept", "application/json")
	            .header("Authorization", "Bearer " + accessToken)
	            .header("Content-Type", "application/json")
	            .POST(BodyPublishers.ofString(json))
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