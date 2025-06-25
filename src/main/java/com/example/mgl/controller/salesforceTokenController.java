package com.example.mgl.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.mgl.model.requestData;
import com.example.mgl.service.salesforceTokenService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/salesforce")
public class salesforceTokenController {

	private final salesforceTokenService salesforceService;

	@Autowired
	public salesforceTokenController(salesforceTokenService salesforceService) {
		this.salesforceService = salesforceService;
	}

	@PostMapping("/token")
	public ResponseEntity<JsonNode> getAccessToken() throws IOException, InterruptedException {
		JsonNode node = salesforceService.getAccessToken();
		return ResponseEntity.ok(node);

	}


}
