package com.example.mgl.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mgl.service.salesforceCreateDataService;
import com.example.mgl.service.salesforceTokenService;
import com.example.mgl.service.salesforceUploadCompleteService;
import com.example.mgl.service.salesforceUploadDataService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/salesforce")
public class salesforceUploadCompleteController {
	@Autowired
	private final salesforceUploadCompleteService uploadCompleteService;
	@Autowired
	private final salesforceCreateDataService mservice;
	@Autowired
	private final salesforceUploadDataService uploadservice;
	@Autowired
	private salesforceTokenService salesforceService;
	@Autowired
	private final ObjectMapper objectmapper;

	public salesforceUploadCompleteController(salesforceUploadCompleteService uploadCompleteService,
			salesforceCreateDataService mservice, salesforceUploadDataService uploadservice,
			salesforceTokenService tokenservice, ObjectMapper objectmapper) {
		super();
		this.uploadCompleteService = uploadCompleteService;
		this.mservice = mservice;
		this.uploadservice = uploadservice;
		this.salesforceService = salesforceService;
		this.objectmapper = objectmapper;
	}

	@PatchMapping("/complete/{jobId}")
	public ResponseEntity<JsonNode> completeUpload(@PathVariable String jobId) throws IOException, InterruptedException {
		JsonNode tokenNode = salesforceService.getAccessToken();
		String accessToken = tokenNode.get("access_token").asText();

		JsonNode salesforceResponse = uploadCompleteService.markUploadCompleteInstance(accessToken, jobId);

		return ResponseEntity.ok(salesforceResponse);
	}
}

	  