package com.example.mgl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mgl.service.salesforceCreateDataService;
import com.example.mgl.service.salesforceTokenService;
import com.example.mgl.service.salesforceUploadDataService;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/salesforce")
public class salesforceUploadDataController {
	@Autowired
	private salesforceTokenService salesforceTokenService;
	@Autowired
	private salesforceCreateDataService mservice;
	@Autowired
	private salesforceUploadDataService uploadService;

	@Autowired
	public salesforceUploadDataController(com.example.mgl.service.salesforceTokenService salesforceTokenService,
			salesforceCreateDataService mservice, salesforceUploadDataService uploadService) {
		super();
		this.salesforceTokenService = salesforceTokenService;
		this.mservice = mservice;
		this.uploadService = uploadService;
	}

	@PutMapping("/upload/{jobId}")
	public ResponseEntity<String> uploadJobData(@PathVariable String jobId, @RequestBody String csvData) {
		JsonNode tokenNode = salesforceTokenService.getAccessToken();
		String accessToken = tokenNode.get("access_token").asText();

		String response = uploadService.uploadCsvToInstance2(accessToken, jobId, csvData);
		return ResponseEntity.status(HttpStatus.CREATED).body("CSV uploaded successfully for Job" + ":" + jobId);
	}

	@PostMapping("/create")
	public ResponseEntity<String> createAndUploadJobData2(@RequestBody String csvData, String externalfieldname) {
		try {
			JsonNode tokenNode = salesforceTokenService.getAccessToken();
			String accessToken = tokenNode.get("access_token").asText();

			JsonNode jobResponse = mservice.createJob2(accessToken, csvData, externalfieldname);
			String jobId = jobResponse.get("id").asText();

			String uploadResponse = uploadService.uploadCsvToInstance2(accessToken, jobId, csvData);

			return ResponseEntity.status(HttpStatus.CREATED)
					.body("CSV create successfully to Instance 2 " + jobId + "and uploaded");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to create CSV to Instance 2: " + e.getMessage());
		}
	}
}
