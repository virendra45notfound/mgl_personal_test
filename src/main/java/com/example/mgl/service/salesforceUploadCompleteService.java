package com.example.mgl.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class salesforceUploadCompleteService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${salesforce.instance-url}")
    private String instanceUrl;

    public salesforceUploadCompleteService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    public JsonNode markUploadCompleteInstance(String accessToken, String jobId) {
        return markUploadComplete(accessToken, jobId, "Instance");
    }

    public JsonNode markUploadCompleteInstance2(String accessToken, String jobId) {
        return markUploadComplete(accessToken, jobId, "Instance 2");
    }

    private JsonNode markUploadComplete(String accessToken, String jobId, String label) {
        String url = instanceUrl + "/services/data/v59.0/jobs/ingest/" + jobId;

        String body = "{\"state\":\"UploadComplete\"}";

        try {
            String response = webClient.patch()
                    .uri(url)
                    .headers(headers -> {
                        headers.setBearerAuth(accessToken);
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.setAccept(java.util.Collections.singletonList(MediaType.APPLICATION_JSON));
                    })
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); 

            return objectMapper.readTree(response);
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Failed to complete upload for " + label + ": " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to complete upload for " + label + ": " + e.getMessage(), e);
        }
    }
}
