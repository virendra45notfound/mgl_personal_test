package com.example.mgl.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class salesforceGetAllInformationService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${salesforce.instance-url}")
    private String instanceUrl;

    public salesforceGetAllInformationService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public JsonNode getAllJobs(String accessToken) {
        String url = instanceUrl + "/services/data/v59.0/jobs/ingest";

        try {
            String response = webClient
                .get()
                .uri(url)
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(String.class)
                .block(); 

            return objectMapper.readTree(response);

        } catch (WebClientResponseException e) {
            throw new RuntimeException("Salesforce API error: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch all jobs: " + e.getMessage(), e);
        }
    }
}
