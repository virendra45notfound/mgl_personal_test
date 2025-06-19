package com.example.mgl.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	  @Value("${salesforce.token-url}")
	  private String tokenUrl;

    @Bean
    WebClient salesforceWebClient(WebClient.Builder builder) {
	    return builder
	      .baseUrl(tokenUrl)
	      .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	      .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
	      .build();
	  }
}
