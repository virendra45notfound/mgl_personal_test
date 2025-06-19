package com.example.mgl.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.http.*;

@Service
public class salesforceGetAllInformationService {
	private final HttpClient client = HttpClient.newHttpClient();

	@Value("${salesforce.instance-url}")
	private String instanceUrl;

	public JsonNode getAllJobs(String accessToken) {

		String url = instanceUrl + "/services/data/v59.0/jobs/ingest";

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
