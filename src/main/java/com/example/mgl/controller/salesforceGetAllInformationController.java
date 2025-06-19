package com.example.mgl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mgl.service.salesforceGetAllInformationService;
import com.example.mgl.service.salesforceTokenService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping("/salesforce")
public class salesforceGetAllInformationController {

	@Autowired
	private salesforceTokenService tokenservice;
	@Autowired
	private salesforceGetAllInformationService getAllinfo;

	@GetMapping("/jobs")
	public ResponseEntity<JsonNode> getAllJobs() {
		try {
			JsonNode tokenNode = tokenservice.getAccessToken();
			String accessToken = tokenNode.get("access_token").asText();

			JsonNode result = getAllinfo.getAllJobs(accessToken);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode errorNode = mapper.createObjectNode();
			errorNode.put("error", "Failed to fetch job details");
			errorNode.put("message", e.getMessage());
			return ResponseEntity.badRequest().body(errorNode);
		}
	}
}

// Something's wrong
