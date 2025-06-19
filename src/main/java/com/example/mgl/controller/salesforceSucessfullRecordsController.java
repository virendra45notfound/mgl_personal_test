package com.example.mgl.controller;

import com.example.mgl.service.salesforceSucessfullRecordsService;
import com.example.mgl.service.salesforceTokenService;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/salesforce")
public class salesforceSucessfullRecordsController {
	private final salesforceSucessfullRecordsService resultService;

	public salesforceSucessfullRecordsController(salesforceSucessfullRecordsService resultService) {
		super();
		this.resultService = resultService;
	}

	@GetMapping("/success/{jobId}")
	public ResponseEntity<String> fetchSuccessfulRecords(@PathVariable String jobId) {
		String result = resultService.getSuccessfulResults(jobId);
		return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(result);
	}
}
