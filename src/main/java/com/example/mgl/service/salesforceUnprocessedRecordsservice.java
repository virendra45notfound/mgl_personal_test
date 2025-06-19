package com.example.mgl.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;

@Service
public class salesforceUnprocessedRecordsservice {
	private final RestTemplate restTemplate = new RestTemplate();
	@Value("${salesforce.instance-url}")
	private String instanceUrl;

	public String getUnprocessedRecords(String jobId, String accessToken) {
		String version = "v59.0";
		String url = instanceUrl + "/services/data/" + version + "/jobs/ingest/" + jobId + "/unprocessedrecords/";

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);

		HttpEntity<Void> entity = new HttpEntity<>(headers);

		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

			String responseBody = response.getBody();
			if (responseBody == null || responseBody.isBlank()) {
				return "No unprocessed records found.";
			}

			String[] lines = responseBody.split("\\r?\\n");
			int totalRecords = lines.length > 1 ? lines.length - 1 : 0;

			return "Total  number of unprocessed records: " + totalRecords + "\n\nRecords:\n" + responseBody;

		} catch (HttpStatusCodeException ex) {
			return "Error response from Salesforce: " + ex.getStatusCode() + " - " + ex.getResponseBodyAsString();
		}
	}
}