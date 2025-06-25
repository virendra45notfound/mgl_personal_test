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
public class sendFlashSmsService {
		
	@Value("${tubelight.plain-url}")		private String tubePlainUrl;
	@Value("${tubelight.sender.id}")		private String tubeSender;
	@Value("${tubelight.mobile}")			private String tubeMobile;
	@Value("${tubelight.username}")	private String tubeUserName;
	@Value("${tubelight.password}")		private String tubePassword;
	@Value("${tubelight.type.flash}")	private String tubeTypeFlash;

	
	private final HttpClient client = HttpClient.newHttpClient();
	
	public String sendFlashMessage(String message) throws IOException, InterruptedException {

		String finalUrl = UriComponentsBuilder.fromUriString(tubePlainUrl)
							.queryParam("username", tubeUserName)
							.queryParam("password", tubePassword)
							.queryParam("sender", tubeSender)
							.queryParam("type", tubeTypeFlash)
							.queryParam("mobile", tubeMobile)
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
