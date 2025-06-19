package com.example.mgl.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class salesforcefailedRecordsService {

    private final WebClient webClient;

    @Value("${salesforce.instance-url}")
    private String instanceUrl;

    public salesforcefailedRecordsService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String getFailedResults(String jobId, String accessToken) {
        String version = "v59.0";
        String url = instanceUrl + "/services/data/" + version + "/jobs/ingest/" + jobId + "/failedResults/";

        try {
            String responseBody = webClient.get()
                    .uri(url)
                    .headers(headers -> headers.setBearerAuth(accessToken))
                    .accept(MediaType.TEXT_PLAIN)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); // blocking to keep behavior like RestTemplate

            if (responseBody == null || responseBody.isBlank()) {
                return "No failed records found.";
            }

            String[] lines = responseBody.split("\\r?\\n");
            int totalRecords = lines.length > 1 ? lines.length - 1 : 0;

            return "Total  number of failed records: " + totalRecords + "\n\nRecords:\n" + responseBody;

        } catch (WebClientResponseException ex) {
            return "Error response from Salesforce: " + ex.getStatusCode() + " - " + ex.getResponseBodyAsString();
        } catch (Exception ex) {
            return "Unexpected error occurred: " + ex.getMessage();
        }
    }
}
