package com.example.mgl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class salesforceGetJobResponseService {

    private static final Logger logger = LoggerFactory.getLogger(salesforceGetJobResponseService.class);

    private final salesforceTokenService tokenservice;
    private final salesforcefailedRecordsService failedrecords;
    private final salesforceSucessfullRecordsService resultservice;
    private final salesforceUnprocessedRecordsservice unprocessed;

    private final Map<String, Function<String, String>> responseHandlers;

    public salesforceGetJobResponseService(
            salesforceTokenService tokenservice,
            salesforcefailedRecordsService failedrecords,
            salesforceSucessfullRecordsService resultservice,
            salesforceUnprocessedRecordsservice unprocessed) {

        this.tokenservice = tokenservice;
        this.failedrecords = failedrecords;
        this.resultservice = resultservice;
        this.unprocessed = unprocessed;

        // Lazy access token supplier inside lambdas
        this.responseHandlers = new HashMap<>();
        responseHandlers.put("s", jobId -> {
            logger.info("Fetching Successful Records for Job Id: {}", jobId);
            return resultservice.getSuccessfulResults(jobId);
        });
        responseHandlers.put("f", jobId -> {
            logger.info("Fetching Failed Records for Job Id: {}", jobId);
            String accessToken = tokenservice.getAccessToken().get("access_token").asText();
            return failedrecords.getFailedResults(jobId, accessToken);
        });
        responseHandlers.put("u", jobId -> {
            logger.info("Fetching Unprocessed Records for Job Id: {}", jobId);
            String accessToken = tokenservice.getAccessToken().get("access_token").asText();
            return unprocessed.getUnprocessedRecords(jobId, accessToken);
        });
    }

    public ResponseEntity<String> getJobResponse(String jobId, String responseType) {
        Function<String, String> handler = responseHandlers.get(responseType.toLowerCase());

        if (handler == null) {
            logger.error("Invalid response type received: {}", responseType);
            return ResponseEntity.badRequest().body("Error, Could not find the Path");
        }

        try {
            String response = handler.apply(jobId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error processing request: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
