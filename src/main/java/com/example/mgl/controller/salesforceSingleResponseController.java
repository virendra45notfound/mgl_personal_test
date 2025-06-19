package com.example.mgl.controller;

import com.example.mgl.service.salesforceSingleResponseService;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/salesforce")
public class salesforceSingleResponseController {
	@Autowired
	private salesforceSingleResponseService singleresponse;

	@Autowired
	public salesforceSingleResponseController(salesforceSingleResponseService singleresponse) {
		super();
		this.singleresponse = singleresponse;
	}

//	@PostMapping("/processComplete")
//	public ResponseEntity<String> processComplete(@RequestBody String csvData) {
//		singleresponse.handleFullSalesforceJob(csvData);
//		return ResponseEntity.ok("Process complete and CSV files added for instance 1.");
//	}

//	@PostMapping("/processComplete2")
//	public ResponseEntity<String> processComplete2(@RequestBody String csvData) {
//		singleresponse.handleFullSalesforceJob2(csvData);
//		return ResponseEntity.ok("Process complete and CSV files added for instance 2");
//	}
}
	