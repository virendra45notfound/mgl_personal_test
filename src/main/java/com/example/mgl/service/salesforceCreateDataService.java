package com.example.mgl.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

@Service
public class salesforceCreateDataService {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${salesforce.instance-url}")
    private String instanceUrl;

 
    public salesforceCreateDataService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    public JsonNode createJob2(String accessToken, String objectName, String externalIdFieldName) {
        String url = instanceUrl + "/services/data/v59.0/jobs/ingest";

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("operation", "upsert");
        requestBody.put("object", objectName);
        requestBody.put("externalIdFieldName", externalIdFieldName);
        requestBody.put("contentType", "CSV");
        requestBody.put("lineEnding", "LF");

        try {
            String response = webClient.post()
                    .uri(url)
                    .headers(headers -> {
                        headers.setBearerAuth(accessToken);
                        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
                    })
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return objectMapper.readTree(response);

        } catch (WebClientResponseException e) {
            throw new RuntimeException("Salesforce API error: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create job for object " + objectName + ": " + e.getMessage(), e);
        }
    }
//	@Autowired
//	private final RestTemplate restTemplate;
//
//	@Value("${salesforce.instance-url}")
//	private String instanceUrl;
//
//	public salesforceCreateDataService(RestTemplateBuilder builder) {
//		this.restTemplate = builder.build();
//	}
//
//	public JsonNode createJob2(String accessToken, String objectName, String externalIdFieldName) {
//		String url = instanceUrl + "/services/data/v59.0/jobs/ingest";
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.setBearerAuth(accessToken);
//		headers.setContentType(MediaType.APPLICATION_JSON);
//
//		Map<String, String> requestBody = new HashMap<>();
//		requestBody.put("operation", "upsert");
//		requestBody.put("object", objectName);
//		requestBody.put("externalIdFieldName", externalIdFieldName);
//		requestBody.put("contentType", "CSV");
//		requestBody.put("lineEnding", "LF");
//
//		HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);
//
//		try {
//			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
//			return new ObjectMapper().readTree(response.getBody());
//		} catch (Exception e) {
//			throw new RuntimeException("Failed to create job for object " + objectName + ": " + e.getMessage(), e);
//		}
//	}
}