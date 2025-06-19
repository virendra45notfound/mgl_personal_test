package com.example.mgl.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
 

import com.fasterxml.jackson.databind.JsonNode;

@Service
public class salesforceGetJobResponseService {
	private static final Logger logger = LoggerFactory.getLogger(SapMiddlewareService.class);

	private final salesforceTokenService tokenservice;
	private final salesforcefailedRecordsService failedrecords;
	private final salesforceSucessfullRecordsService resultservice;
	private final salesforceUnprocessedRecordsservice unprocessed;
	
	public salesforceGetJobResponseService(
			salesforceTokenService tokenservice,
			salesforcefailedRecordsService failedrecords,
			salesforceSucessfullRecordsService resultservice,
			salesforceUnprocessedRecordsservice unprocessed) {
		
		this.tokenservice = tokenservice;
		this.failedrecords = failedrecords;
		this.resultservice = resultservice;
		this.unprocessed = unprocessed;
	}
	
	public ResponseEntity<String> getJobResponse(String jobId, String responseType) throws IOException, InterruptedException{
		
		JsonNode tokenNode = tokenservice.getAccessToken();
		String accessToken = tokenNode.get("access_token").asText();
		if(responseType.equalsIgnoreCase("s")) {
			logger.info("Received Request for Fetching Successful Records with Job Id: " + jobId);
			try {
				String response = resultservice.getSuccessfulResults(jobId);
				return ResponseEntity.ok(response);
			} catch (Exception e) {
				return ResponseEntity.internalServerError().body(e.getMessage());
			}
			
		}else if(responseType.equalsIgnoreCase("f")) {
			logger.info("Received Request for Fetching Failed Records with Job Id: " + jobId);
			try {
				String response = failedrecords.getFailedResults(jobId, accessToken);
				return ResponseEntity.ok(response);
			} catch (Exception e) {
				return ResponseEntity.internalServerError().body(e.getMessage());
			}
			
		}
		else if(responseType.equalsIgnoreCase("u")) {
			logger.info("Received Request for Fetching Unprocessed Records with Job Id: " + jobId);
			try {
				String response = unprocessed.getUnprocessedRecords(jobId, accessToken);
				return ResponseEntity.ok(response);
			} catch (Exception e) {
				return ResponseEntity.internalServerError().body(e.getMessage());
			}
			
		}

		else {
			logger.error("Could not find the Requested Resource");
			return ResponseEntity.badRequest().body("Error, Could not find the Path");
		}
	}
	
}
