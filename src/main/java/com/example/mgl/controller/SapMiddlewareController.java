package com.example.mgl.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mgl.service.SapMiddlewareService;
import com.example.mgl.service.salesforceBatchService;
import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/sap")
public class SapMiddlewareController {
	private static final Logger logger = LoggerFactory.getLogger(SapMiddlewareController.class);
	private final SapMiddlewareService sapMiddlewareService;

	public SapMiddlewareController(SapMiddlewareService sapMiddlewareService) {
		this.sapMiddlewareService = sapMiddlewareService;
	}

	@PostMapping(value = "/upload/account", consumes = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> uploadAccount(@RequestBody String body) {
		return ResponseEntity.ok(sapMiddlewareService.processAndUpload("Account", body, "BP_Number__c"));
	}

	@PostMapping(value = "/upload/connection", consumes = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> uploadConnection(@RequestBody String body) {
		return ResponseEntity.ok(sapMiddlewareService.processAndUpload("Connection__c", body, "Connection_Code__c"));
	}

	@PostMapping(value = "/upload/premise", consumes = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> uploadPermissis(@RequestBody String body) {
		return ResponseEntity.ok(sapMiddlewareService.processAndUpload("Premise__c", body, "Premise_Code__c"));
	}
	@PostMapping(value = "/upload/installation", consumes = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> uploadInstallation(@RequestBody String body) {
		return ResponseEntity.ok(sapMiddlewareService.processAndUpload("Installation__c", body, "Installation_Code__c"));
	}
	@PostMapping(value = "/upload/contract", consumes = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> uploadContract(@RequestBody String body) {
		return ResponseEntity.ok(sapMiddlewareService.processAndUpload("ServiceContract", body, "Contract_Account_Code__c"));
	}
	@PostMapping(value = "/upload/billing", consumes = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> uploadBilling(@RequestBody String body) {
		return ResponseEntity.ok(sapMiddlewareService.processAndUpload("Billing__c", body, "Billing_Code__c"));
	}
}
