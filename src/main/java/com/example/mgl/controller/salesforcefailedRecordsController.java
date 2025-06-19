package com.example.mgl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mgl.service.salesforceTokenService;
import com.example.mgl.service.salesforcefailedRecordsService;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/salesforce")
public class salesforcefailedRecordsController {
	@Autowired
	private salesforcefailedRecordsService failedrecords;
	@Autowired
	private salesforceTokenService tokenservice;

	@Autowired
	public salesforcefailedRecordsController(salesforcefailedRecordsService failedrecords,
			salesforceTokenService tokenservice) {
		super();
		this.failedrecords = failedrecords;
		this.tokenservice = tokenservice;
	}

	@GetMapping("/failedRecords/{jobId}")
	public ResponseEntity<String> getAllFailedRecords(@PathVariable String jobId) {
		JsonNode tokenNode = tokenservice.getAccessToken();
		String accessToken = tokenNode.get("access_token").asText();

		String csvResults = failedrecords.getFailedResults(jobId, accessToken);
		return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(csvResults);
	}
}
