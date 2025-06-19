package com.example.mgl.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class salesforceGetStatusService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public salesforceGetStatusService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.build(); 
        this.objectMapper = objectMapper;
    }

    public JsonNode getJobStatus(String jobId, String accessToken, String instanceUrl) throws Exception {
        String url = instanceUrl + "/services/data/v59.0/jobs/ingest/" + jobId;

        try {
            String response = webClient.get()
                    .uri(url)
                    .headers(headers -> {
                        headers.setBearerAuth(accessToken);
                        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
                    })
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); 

            return objectMapper.readTree(response);

        } catch (WebClientResponseException e) {
            throw new Exception("Failed to fetch job status: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new Exception("Unexpected error occurred while fetching job status: " + e.getMessage(), e);
        }
    }
}
