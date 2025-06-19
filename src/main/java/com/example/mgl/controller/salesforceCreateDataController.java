package com.example.mgl.controller;

import java.io.IOException;
import java.util.Map;

import javax.management.ObjectName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.mgl.service.salesforceSingleResponseService;
import com.example.mgl.service.salesforceTokenService;
import com.example.mgl.service.salesforceUploadCompleteService;
import com.example.mgl.service.salesforceUploadDataService;
import com.example.mgl.model.requestData;
import com.example.mgl.service.salesforceCreateDataService;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/salesforce")
public class salesforceCreateDataController {
	@Autowired
	private salesforceCreateDataService mService;
	@Autowired
	private salesforceTokenService tokenService;
	@Autowired
	private salesforceSingleResponseService singleresponse;

	public salesforceCreateDataController(salesforceCreateDataService mService, salesforceTokenService tokenService,
			salesforceSingleResponseService singleresponse) {
		super();
		this.mService = mService;
		this.tokenService = tokenService;
		this.singleresponse = singleresponse;
	}

	@PostMapping("/account")
	public ResponseEntity<JsonNode> createSalesforceAccountJob() throws IOException, InterruptedException {
		JsonNode tokenNode = tokenService.getAccessToken();
		String accessToken = tokenNode.get("access_token").asText();
		JsonNode jobResponse = mService.createJob2(accessToken, "Account", "BP_Number__c");
		return ResponseEntity.ok(jobResponse);
	}

	@PostMapping("/connection")
	public ResponseEntity<JsonNode> createSalesforceConnectionJob() throws IOException, InterruptedException {
		JsonNode tokenNode = tokenService.getAccessToken();
		String accessToken = tokenNode.get("access_token").asText();
		JsonNode jobResponse = mService.createJob2(accessToken, "Connection__c", "Connection_Code__c");
		return ResponseEntity.ok(jobResponse);
	}
	@PostMapping("/premise")
	public ResponseEntity<JsonNode> createSalesforcePremiseJob() throws IOException, InterruptedException {
		JsonNode tokenNode = tokenService.getAccessToken();
		String accessToken = tokenNode.get("access_token").asText();
		JsonNode jobResponse = mService.createJob2(accessToken, "Premise__c","Premise_Code__c");
		return ResponseEntity.ok(jobResponse);
	}
	@PostMapping("/installation")
	public ResponseEntity<JsonNode> createSalesforceInstallationJob() throws IOException, InterruptedException {
		JsonNode tokenNode = tokenService.getAccessToken();
		String accessToken = tokenNode.get("access_token").asText();
		JsonNode jobResponse = mService.createJob2(accessToken, "Installation__c","Installation_Code__c");
		return ResponseEntity.ok(jobResponse);
	}
	@PostMapping("/contract")
	public ResponseEntity<JsonNode> createSalesforceContractJob() throws IOException, InterruptedException {
		JsonNode tokenNode = tokenService.getAccessToken();
		String accessToken = tokenNode.get("access_token").asText();
		JsonNode jobResponse = mService.createJob2(accessToken, "ServiceContract","Contract_Account_Code__c");
		return ResponseEntity.ok(jobResponse);
	}
	@PostMapping("/billing")
	public ResponseEntity<JsonNode> createSalesforceBillingJob() throws IOException, InterruptedException {
		JsonNode tokenNode = tokenService.getAccessToken();
		String accessToken = tokenNode.get("access_token").asText();
		JsonNode jobResponse = mService.createJob2(accessToken, "Billing__c","Billing_Code__c");
		return ResponseEntity.ok(jobResponse);
	}
}