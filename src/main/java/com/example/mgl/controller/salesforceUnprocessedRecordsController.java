package com.example.mgl.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mgl.service.salesforceTokenService;
import com.example.mgl.service.salesforceUnprocessedRecordsservice;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/salesforce")
public class salesforceUnprocessedRecordsController {
	private salesforceUnprocessedRecordsservice unprocessed;
	private salesforceTokenService tokenservice;

	public salesforceUnprocessedRecordsController(salesforceUnprocessedRecordsservice unprocessed,
			salesforceTokenService tokenservice) {
		super();
		this.unprocessed = unprocessed;
		this.tokenservice = tokenservice;
	}

	@GetMapping("/unprocessed/{jobId}")
	public ResponseEntity<String> getUnprocessedRecords(@PathVariable String jobId) {
		JsonNode tokenNode = tokenservice.getAccessToken();
		String accessToken = tokenNode.get("access_token").asText();

		String csvResult = unprocessed.getUnprocessedRecords(jobId, accessToken);
		return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(csvResult);
	}
}
