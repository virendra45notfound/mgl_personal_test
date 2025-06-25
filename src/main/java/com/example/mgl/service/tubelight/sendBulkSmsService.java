package com.example.mgl.service.tubelight;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;

@Service
public class sendBulkSmsService {
	
	@Value("${tubelight.bulk-url}")		private String tubeBulkUrl;
	
	private final HttpClient client = HttpClient.newHttpClient();
	
	public String sendBulkMessage(JsonNode message) throws IOException, InterruptedException {

		String finalUrl = UriComponentsBuilder.fromUriString(tubeBulkUrl)
							.toUriString();
		String jsonPayload = message.toString();
		HttpRequest req = HttpRequest.newBuilder()
	            .uri(URI.create(finalUrl))
	            .header("Accept",       		"text/plain")
	            .header("Content-Type", "application/json")  
	            .header("key",          		"API Key")
	            .header("version",      		"1.0")
	            .header("channel",      		"0")
	            .header("peId",         		"XXX")
	            .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
	            .build();
		
		try {
	        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
	        return resp.body();
	    } catch (HttpStatusCodeException ex) {
			return "Error" + ex.getResponseBodyAsString();
		}
	}

}
