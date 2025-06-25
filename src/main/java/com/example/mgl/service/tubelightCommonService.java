package com.example.mgl.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;

import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


@Service
public class tubelightCommonService {

    private final RestTemplate restTemplate;

		@Value("${tubelight.plain-url}")						private String tubePlainUrl;
		@Value("${tubelight.unicode-url}")					private String tubeUnicodeUrl;
		@Value("${tubelight.bulk-url}")						private String tubeBulkUrl;
		@Value("${tubelight.balance-url}")					private String tubeBalanceUrl;
		@Value("${tubelight.delivery-status-url}")		private String tubeDeliveryUrl;
//		@Value("${tubelight.}")									private String tubeApiKey;
		@Value("${tubelight.sender.id}")						private String tubeSender;
		@Value("${tubelight.mobile}")							private String tubeMobile;
//		@Value("${tubelight.}")									private String tubePeId;
//		@Value("${tubelight.}")									private String tubeTempId;
		@Value("${tubelight.username}")					private String tubeUserName;
		@Value("${tubelight.password}")						private String tubePassword;
		@Value("${tubelight.type.text}")					private String tubeTypeText;
		@Value("${tubelight.type.flash}")					private String tubeTypeFlash;
		@Value("${tubelight.type.unicode}")				private String tubeTypeUnicode;
		@Value("${tubelight.type.unicode-flash}")		private String tubeTypeUnicodeFlash;
		
		private final HttpClient client = HttpClient.newHttpClient();

    tubelightCommonService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    {
    	
//		public JsonNode sendTextMessage(String message, String type, String urlKey) throws IOException, InterruptedException {
//			
//			ObjectMapper mapper = new ObjectMapper();
//			ObjectNode errorResponse = mapper.createObjectNode();
//			ObjectNode successResponse = mapper.createObjectNode();
//			
//			Map.Entry<String,String> urlAndType = switch (urlKey) {
//	        case "plain" -> {
//	            String base = tubePlainUrl;
//	            String finalType = switch (type.toLowerCase()) {
//	                case "text"  	-> tubeTypeText;
//	                case "flash" 	-> tubeTypeFlash;
//	                default      	-> throw new IllegalArgumentException("Invalid Type for plain SMS: " + type);
//	            };
//	            yield new AbstractMap.SimpleEntry<>(base, finalType);
//	        }
//	        case "unicode" -> {
//	            String base = tubeUnicodeUrl;
//	            String finalType = switch (type.toLowerCase()) {
//	                case "unicode"       		-> tubeTypeUnicode;
//	                case "unicode-flash" 	-> tubeTypeUnicodeFlash;
//	                default              			-> throw new IllegalArgumentException("Invalid Type for unicode SMS: " + type);
//	            };
//	            yield new AbstractMap.SimpleEntry<>(base, finalType);
//	        }
//	        case "bulk" -> {
//	            
//	            System.out.println("Yet to Implement");
//	            throw new IllegalArgumentException("Unimplemented Bulk API");
//	        }
//	        default -> throw new IllegalArgumentException("Invalid Base Url key: " + urlKey);
//	    };
//
//	    String baseUrl   = urlAndType.getKey();
//	    String finalType = urlAndType.getValue();
//
//			String finalUrl = UriComponentsBuilder.fromUriString(baseUrl)
//								.queryParam("username", tubeUserName)
//								.queryParam("password", tubePassword)
//								.queryParam("sender", tubeSender)
//								.queryParam("type", finalType)
//								.queryParam("mobile", tubeMobile)
//								.queryParam("message", message)
//								.toUriString();
//
//			HttpRequest req = HttpRequest.newBuilder().uri(URI.create(finalUrl)).header("Accept", "text/plain").GET().build();
//			
//			try {
//		        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
//		        return successResponse.put("Response", resp.body());
//		    } catch (HttpStatusCodeException ex) {
//				return errorResponse.put("Error", ex.getResponseBodyAsString());
//			}
//		}
		
    }
		public JsonNode checkBalance() throws IOException, InterruptedException {
			ObjectMapper mapper = new ObjectMapper();
			String url = UriComponentsBuilder.fromUriString(tubeBalanceUrl)
								.queryParam("username", tubeUserName)
								.queryParam("password", tubePassword)
								.toUriString();
//			System.out.println("Url: " + url);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.setAccept(Collections.singletonList(MediaType.TEXT_PLAIN));

			HttpEntity<String> entity = new HttpEntity<>(headers);

			try {
				ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
				return mapper.readTree(response.getBody());
			} catch (Exception e) {
				throw new RuntimeException("Failed to fetch Balance Details: " + e.getMessage());
			}

		}
		
		public String checkDeliveryStatus(String messageId) throws IOException, InterruptedException {
			String url = UriComponentsBuilder.fromUriString(tubeDeliveryUrl)
								.queryParam("username", tubeUserName)
								.queryParam("password", tubePassword)
								.queryParam("messageid", messageId)
								.toUriString();
//			System.out.println("Url: " + url);
//			ObjectMapper mapper = new ObjectMapper();
//			ObjectNode errorResponse = mapper.createObjectNode();
//			ObjectNode successResponse = mapper.createObjectNode();
			
			HttpRequest req = HttpRequest.newBuilder()
		            .uri(URI.create(url))
		            .GET()
		            .build();
			
			try {
		        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
//		        String responseBody = resp.body();
//		        successResponse.put("Response", responseBody);
		        return resp.body();
		    } catch (HttpStatusCodeException ex) {
//		    	errorResponse.put("Error", ex.getResponseBodyAsString());
//				return errorResponse;
		    	return "Error Fetching the Delivery status: " + ex.getMessage();
			}

		}

}
