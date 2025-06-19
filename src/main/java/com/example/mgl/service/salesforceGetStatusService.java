package com.example.mgl.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class salesforceGetStatusService {
	private final HttpClient client = HttpClient.newHttpClient();

	public JsonNode getJobStatus(String jobId, String accessToken, String instanceUrl) throws Exception {
		String url = instanceUrl + "/services/data/v59.0/jobs/ingest/" + jobId;
		
		HttpRequest req = HttpRequest.newBuilder()
	            .uri(URI.create(url))
	            .header("Authorization", "Bearer " + accessToken)
	            .header("Content-Type", "application/json")
	            .GET()
	            .build();

		try {
			HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
			return new ObjectMapper().readTree(resp.body());

		} catch (HttpClientErrorException e) {
			throw new Exception("Failed to fetch job status: " + e.getResponseBodyAsString());
		}
	}
}
