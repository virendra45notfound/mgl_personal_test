package com.example.mgl.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;

@Service
public class salesforceAbortedJobService {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${salesforce.instance-url}")
    private String instanceUrl;

    public salesforceAbortedJobService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.baseUrl(instanceUrl).build();
        this.objectMapper = objectMapper;
    }

    public JsonNode abortJob(String jobId, String accessToken) throws Exception {
        String url = "/services/data/v59.0/jobs/ingest/" + jobId;

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("state", "Aborted");

        
        String response = webClient
            .patch()  
            .uri(url)  
            .header("Authorization", "Bearer " + accessToken)  
            .contentType(MediaType.APPLICATION_JSON)  
            .bodyValue(requestBody)  
            .retrieve()  
            .bodyToMono(String.class)  
            .doOnError(e -> System.err.println("Abort Job Failed: " + e.getMessage()))
            .block(); 

        return objectMapper.readTree(response);  
    }
//	private final RestTemplate restTemplate;
//	private final ObjectMapper objectMapper;
//
//	@Value("${salesforce.instance-url}")
//	private String instanceUrl;
//
//	public salesforceAbortedJobService(RestTemplateBuilder builder, ObjectMapper objectMapper) {
//		this.restTemplate = builder.build();
//		this.objectMapper = objectMapper;
//	}
//
//	public JsonNode abortJob(String jobId, String accessToken) throws Exception {
//		String url = instanceUrl+ "/services/data/v59.0/jobs/ingest/" + jobId;
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.setBearerAuth(accessToken);
//		headers.setContentType(MediaType.APPLICATION_JSON);
//
//		Map<String, String> body = new HashMap<>();
//		body.put("state", "Aborted");
//
//		HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
//
//		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PATCH, entity, String.class);
//
//		return objectMapper.readTree(response.getBody());
//	}
}