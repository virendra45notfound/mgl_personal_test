package com.example.mgl.controller;

import com.example.mgl.service.salesforceAbortedJobService;
import com.example.mgl.service.salesforceTokenService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/salesforce")
public class salesforceAbortedJobController {

	private final salesforceAbortedJobService abortedJobService;
	private final salesforceTokenService tokenService;
	
	public salesforceAbortedJobController(salesforceAbortedJobService abortedJobService,
																  salesforceTokenService tokenService) {
		this.abortedJobService = abortedJobService; 
		this.tokenService = tokenService; 
	}

	@PatchMapping("/abort/{jobId}")
	public ResponseEntity<JsonNode> abortJob(@PathVariable String jobId) {
		try {
			JsonNode tokenNode = tokenService.getAccessToken();
			String accessToken = tokenNode.get("access_token").asText();

			JsonNode result = abortedJobService.abortJob(jobId, accessToken);
			return ResponseEntity.ok(result);

		} catch (Exception e) {
			e.printStackTrace();
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode error = mapper.createObjectNode();
			error.put("error", "Failed to abort job");
			error.put("message", e.getMessage());
			return ResponseEntity.badRequest().body(error);
		}
	}
}
