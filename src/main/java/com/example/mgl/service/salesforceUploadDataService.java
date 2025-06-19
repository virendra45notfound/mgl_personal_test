package com.example.mgl.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class salesforceUploadDataService {

	private final HttpClient client = HttpClient.newHttpClient();
	@Value("${salesforce.instance-url}")
	private String instanceUrl;

	public String uploadCsvToInstance2(String accessToken, String jobId, String csvData) {
		String url = instanceUrl + "/services/data/v59.0/jobs/ingest/" + jobId + "/batches";
		return uploadCsv(accessToken, csvData, url, "Instance ");
	}

	private String uploadCsv(String accessToken, String csvData, String url, String instanceName) {
		String normalizedCsvData = csvData.replaceAll("\\r\\n?", "\n");
		HttpRequest req = HttpRequest.newBuilder()
	            .uri(URI.create(url))
	            .header("Authorization", "Bearer " + accessToken)
	            .header("Content-Type", "text/csv")
	            .PUT(HttpRequest.BodyPublishers.ofString(normalizedCsvData))
	            .build();
		
		try {
			HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
			return resp.body();
		} catch (Exception e) {
			throw new RuntimeException("Failed to upload CSV to " + instanceName + ": " + e.getMessage(), e);
		}
	}
}

