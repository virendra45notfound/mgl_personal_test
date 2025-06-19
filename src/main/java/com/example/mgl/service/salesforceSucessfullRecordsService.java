package com.example.mgl.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Collections;

@Service
public class salesforceSucessfullRecordsService {

    private final WebClient webClient;
    private final salesforceTokenService tokenService;

    @Value("${salesforce.instance-url}")
    private String instanceUrl;

    public salesforceSucessfullRecordsService(salesforceTokenService tokenService, WebClient.Builder webClientBuilder) {
        this.tokenService = tokenService;
        this.webClient = webClientBuilder.build();
    }

    public String getSuccessfulResults(String jobId) {
        JsonNode accessTokenResponse = tokenService.getAccessToken();
        String accessToken = accessTokenResponse.get("access_token").asText();
        String version = "v59.0";

        String url = instanceUrl + "/services/data/" + version + "/jobs/ingest/" + jobId + "/successfulResults/";

        try {
            String responseBody = webClient.get()
                    .uri(url)
                    .headers(headers -> {
                        headers.setBearerAuth(accessToken);
                        headers.setAccept(Collections.singletonList(MediaType.TEXT_PLAIN));
                    })
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); // Blocking to keep behavior same as RestTemplate

            if (responseBody == null || responseBody.isBlank()) {
                return "No successful records found";
            }

            String[] lines = responseBody.split("\\r?\\n");
            int totalRecords = lines.length > 1 ? lines.length - 1 : 0;

            return "Total  number of successful records: " + totalRecords + "\n\nRecords:\n" + responseBody;

        } catch (WebClientResponseException ex) {
            return "Error response from Salesforce: " + ex.getStatusCode() + " - " + ex.getResponseBodyAsString();
        } catch (Exception ex) {
            return "Unexpected error occurred: " + ex.getMessage();
        }
    }
}
