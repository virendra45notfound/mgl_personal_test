package com.example.mgl.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class salesforceDeleteJobService {

    private final WebClient webClient;

    @Value("${salesforce.instance-url}")
    private String instanceUrl;

    public salesforceDeleteJobService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public ResponseEntity<String> deleteJob(String jobId, String accessToken) {
        String version = "v59.0";
        String url = instanceUrl + "/services/data/" + version + "/jobs/ingest/" + jobId;

        try {
            String responseBody = webClient
                .delete()
                .uri(url)
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(String.class)
                .block();

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseBody);

        } catch (WebClientResponseException e) {
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body("Salesforce API error: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete job " + jobId + ": " + e.getMessage());
        }
    }
}
