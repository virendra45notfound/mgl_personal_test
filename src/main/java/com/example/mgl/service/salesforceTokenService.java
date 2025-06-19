package com.example.mgl.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class salesforceTokenService {

    private final WebClient webClient;
    private final String grantType;
    private final String clientId;
    private final String clientSecret;
    private final String username;
    private final String password;

    public salesforceTokenService(
        WebClient salesforceWebClient,
        @Value("${salesforce.grant-type}")    String grantType,
        @Value("${salesforce.client-id}")     String clientId,
        @Value("${salesforce.client-secret}") String clientSecret,
        @Value("${salesforce.username}")      String username,
        @Value("${salesforce.password}")      String password
    ) {
        this.webClient    = salesforceWebClient;
        this.grantType     = grantType;
        this.clientId      = clientId;
        this.clientSecret  = clientSecret;
        this.username      = username;
        this.password      = password;
    }

    public Mono<JsonNode> getAccessTokenReactive() {
        return webClient.post()
            .uri(uriBuilder -> uriBuilder
                .queryParam("grant_type",    grantType)
                .queryParam("client_id",     clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("username",      username)
                .queryParam("password",      password)
                .build()
            )
            .retrieve()
            .bodyToMono(JsonNode.class)   
            .doOnError(err ->
                System.err.println("Token request failed: " + err.getMessage())
            );
    }

    public JsonNode getAccessToken() {
        return getAccessTokenReactive()
            .block();  
    }
}
