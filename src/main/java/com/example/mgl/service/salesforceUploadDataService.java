package com.example.mgl.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class salesforceUploadDataService {

    private final WebClient webClient;

    @Value("${salesforce.instance-url}")
    private String instanceUrl;

    public salesforceUploadDataService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String uploadCsvToInstance2(String accessToken, String jobId, String csvData) {
        String url = instanceUrl + "/services/data/v59.0/jobs/ingest/" + jobId + "/batches";
        return uploadCsv(accessToken, csvData, url, "Instance");
    }

    private String uploadCsv(String accessToken, String csvData, String url, String instanceName) {
        try {
            return webClient.put()
                    .uri(url)
                    .headers(headers -> {
                        headers.setBearerAuth(accessToken);
                        headers.setContentType(MediaType.valueOf("text/csv"));
                    })
                    .bodyValue(csvData)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Failed to upload CSV to " + instanceName + ": "
                    + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload CSV to " + instanceName + ": " + e.getMessage(), e);
        }
    }
}
