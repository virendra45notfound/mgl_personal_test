package com.example.mgl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mgl.service.salesforceGetStatusService;
import com.example.mgl.service.salesforceTokenService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/salesforce")
public class salesforceGetStatusController {

	@Autowired
	private salesforceGetStatusService jobStatusService;

	@Autowired
	private salesforceTokenService tokenService;

	@GetMapping("/status/{jobId}")
	public ResponseEntity<JsonNode> checkJobStatus(@PathVariable String jobId) {
		try {
			JsonNode tokenResponse = tokenService.getAccessToken();
			String accessToken = tokenResponse.get("access_token").asText();
			String instanceUrl = tokenResponse.get("instance_url").asText();

			JsonNode response = jobStatusService.getJobStatus(jobId, accessToken, instanceUrl);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ObjectMapper().createObjectNode().put("error", e.getMessage()));
		}

	}
}
