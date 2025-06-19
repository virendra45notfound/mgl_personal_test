package com.example.mgl.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mgl.service.salesforceGetJobResponseService;

@RestController
@RequestMapping("/salesforce")
public class salesforceGetJobResponseController {

	private final salesforceGetJobResponseService getJobResponse;
	
	public salesforceGetJobResponseController(salesforceGetJobResponseService getJobResponse) {
		this.getJobResponse = getJobResponse;
	}
	
	@GetMapping("/get-records/{jobId}/{responseType}")
	public ResponseEntity<String> getSuccessRecords(@PathVariable String jobId, @PathVariable String responseType){
		try {			
			ResponseEntity<String> response = getJobResponse.getJobResponse(jobId, responseType);
			return response;
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Error : " + e.getMessage());
		}
	}
	
}
