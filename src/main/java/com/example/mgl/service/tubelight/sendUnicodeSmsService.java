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

@Service
public class sendUnicodeSmsService {
	
	@Value("${tubelight.unicode-url}")		private String tubeUnicodeUrl;
	@Value("${tubelight.sender.id}")		private String tubeSender;
	@Value("${tubelight.mobile}")			private String tubeMobile;
	@Value("${tubelight.username}")	private String tubeUserName;
	@Value("${tubelight.password}")		private String tubePassword;
	@Value("${tubelight.type.unicode}")	private String tubeTypeUnicode;
	
	private final HttpClient client = HttpClient.newHttpClient();
	
	public String sendUnicodeMessage(String message) throws IOException, InterruptedException {

		String finalUrl = UriComponentsBuilder.fromUriString(tubeUnicodeUrl)
							.queryParam("username", tubeUserName)
							.queryParam("password", tubePassword)
							.queryParam("type", tubeTypeUnicode)
							.queryParam("mobile", tubeMobile)
							.queryParam("sender", tubeSender)
							.queryParam("message", message)
							.toUriString();

		HttpRequest req = HttpRequest.newBuilder()
				.uri(URI.create(finalUrl))
				.header("Accept", "text/plain")
				.GET()
				.build();
		
		try {
	        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
	        return resp.body();
	    } catch (HttpStatusCodeException ex) {
			return "Error" + ex.getResponseBodyAsString();
		}
	}

}
