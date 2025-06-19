package com.example.mgl.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;

@Service
public class salesforceDeleteJobService {
	private final HttpClient client = HttpClient.newHttpClient();

	@Value("${salesforce.instance-url}")
	private String instanceUrl;

	public ResponseEntity<String> deleteJob(String jobId, String accessToken) {
		String version = "v59.0";
		String url = instanceUrl + "/services/data/" + version + "/jobs/ingest/" + jobId;
		
		HttpRequest req = HttpRequest.newBuilder()
	            .uri(URI.create(url))
	            .header("Authorization", "Bearer " + accessToken)
	            .header("Content-Type", "application/json")
	            .header("Accept", "text/csv")  
	            .DELETE()
	            .build();
		try {
			HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
			return ResponseEntity
			        .status(resp.statusCode())
			        .body(resp.body());

		} catch(Exception e) {
			return ResponseEntity.internalServerError().body("Error : " + e.getMessage());
		}
	}
}
