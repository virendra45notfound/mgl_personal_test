package com.example.mgl.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

@Service
public class salesforcefailedRecordsService {
	private final HttpClient client = HttpClient.newHttpClient();
	@Value("${salesforce.instance-url}")
	private String instanceUrl;

	public String getFailedResults(String jobId, String accessToken) throws IOException, InterruptedException {
	    String version = "v59.0";
	    String url = instanceUrl + "/services/data/" + version + "/jobs/ingest/" + jobId + "/failedResults/";

	    HttpRequest req = HttpRequest.newBuilder()
	            .uri(URI.create(url))
	            .header("Authorization", "Bearer " + accessToken)
	            .header("Accept", "text/csv")  
	            .GET()
	            .build();

	    try {
	        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
	        String responseBody = resp.body();
	        if (responseBody == null || responseBody.isBlank()) {
	            return "No failed records found.";
	        }
	        
	        String[] lines = responseBody.split("\\r?\\n");
	        int totalRecords = lines.length > 1 ? lines.length - 1 : 0;

	        return "Total number of failed records: " + totalRecords + "\n\nRecords:\n" + responseBody;
//	        return responseBody;
	    } catch (HttpStatusCodeException ex) {
			return "Error response from Salesforce: " + ex.getStatusCode() + " - " + ex.getResponseBodyAsString();
		}
	}
}
