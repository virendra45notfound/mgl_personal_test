package com.example.mgl.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mgl.service.salesforceDeleteJobService;
import com.example.mgl.service.salesforceTokenService;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/salesforce")
public class salesforceDeleteJobController {

	private salesforceDeleteJobService deleteservice;
	private salesforceTokenService tokenservice;

	public salesforceDeleteJobController(salesforceDeleteJobService deleteservice,
			salesforceTokenService tokenservice) {
		super();
		this.deleteservice = deleteservice;
		this.tokenservice = tokenservice;
	}

	@DeleteMapping("/delete/{jobId}")
	public ResponseEntity<String> deleteJob(@PathVariable String jobId) {
		JsonNode tokenNode = tokenservice.getAccessToken();
		String accessToken = tokenNode.get("access_token").asText();

		ResponseEntity<String> response = deleteservice.deleteJob(jobId, accessToken);

		if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
			return ResponseEntity.ok("Job deleted successfully"+":"+jobId);
		} else {
			return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
		}
	}
}
