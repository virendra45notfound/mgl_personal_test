package com.example.mgl.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import org.springframework.http.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class salesforceSingleResponseService {

//	private static final int LINES_PER_CHUNK = 20000;
//	@Autowired
//	private salesforceTokenService tokenservice;
//	private salesforceCreateDataService mservice;
//	private salesforceUploadDataService uploadservice;
//	private salesforceUploadCompleteService uploadCompleteService;
//	private ObjectMapper objectMapper;
//
//	public salesforceSingleResponseService(salesforceTokenService tokenservice, salesforceCreateDataService mservice,
//			salesforceUploadDataService uploadeservice, salesforceUploadCompleteService uploadCompleteService,
//			ObjectMapper objectMapper) {
//		super();
//		this.tokenservice = tokenservice;
//		this.mservice = mservice;
//		this.uploadservice = uploadeservice;
//		this.uploadCompleteService = uploadCompleteService;
//		this.objectMapper = objectMapper;
//	}

//	public void handleFullSalesforceJob(String csvData) {
//		try {
//			JsonNode tokenResponse = tokenservice.getAccessToken2();
//			if (tokenResponse == null || !tokenResponse.has("access_token")) {
//				throw new RuntimeException("Failed to retrieve access token");
//			}
//
//			String accessToken = tokenResponse.get("access_token").asText();
//			BufferedReader reader = new BufferedReader(new StringReader(csvData));
//			List<String> batchLines = new ArrayList<>();
//			String header = reader.readLine();
//			String line;
//			int batchNumber = 1;
//
//			while ((line = reader.readLine()) != null) {
//				batchLines.add(line);
//
//				if (batchLines.size() >= LINES_PER_CHUNK) {
//					uploadBatch(batchLines, header, accessToken, batchNumber++);
//					batchLines.clear();
//				}
//			}
//
//			if (!batchLines.isEmpty()) {
//				uploadBatch(batchLines, header, accessToken, batchNumber);
//			}
//
//		} catch (Exception e) {
//			System.out.println("Error during Salesforce processing for first instance: " + e.getMessage());
//			throw new RuntimeException("Salesforce batch job processing failed for first instance", e);
//		}
//	}

//	public void handleFullSalesforceJob2(String csvData) {
//		try {
//			JsonNode tokenResponse = tokenservice.getAccessToken2();
//			if (tokenResponse == null || !tokenResponse.has("access_token")) {
//				throw new RuntimeException("Failed to retrieve access token");
//			}
//
//			String accessToken = tokenResponse.get("access_token").asText();
//			BufferedReader reader = new BufferedReader(new StringReader(csvData));
//			List<String> batchLines = new ArrayList<>();
//			String header = reader.readLine();
//			String line;
//			int batchNumber = 1;
//
//			while ((line = reader.readLine()) != null) {
//				batchLines.add(line);
//
//				if (batchLines.size() >= LINES_PER_CHUNK) {
//					uploadBatchInstance2(batchLines, header, accessToken, batchNumber++);
//					batchLines.clear();
//				}
//			}
//
//			if (!batchLines.isEmpty()) {
//				uploadBatchInstance2(batchLines, header, accessToken, batchNumber);
//			}
//
//		} catch (Exception e) {
//			System.out.println("Error during Salesforce processing for second instance: " + e.getMessage());
//			throw new RuntimeException("Salesforce batch job processing failed for second instance", e);
//		}
//	}

//	private void uploadBatch(List<String> batchLines, String header, String accessToken, int batchNumber) {
//		try {
//			StringBuilder chunkDataBuilder = new StringBuilder();
//			chunkDataBuilder.append(header).append("\r\n");
//			for (String dataLine : batchLines) {
//				chunkDataBuilder.append(dataLine).append("\r\n");
//			}
//
//			String chunkData = chunkDataBuilder.toString();
//			System.out.println("Uploading batch " + batchNumber + " with " + batchLines.size() + " lines");
//
//			JsonNode jobResponse = mservice.createJob(accessToken);
//			if (jobResponse == null || !jobResponse.has("id")) {
//				throw new RuntimeException("Failed to create Salesforce job for batch " + batchNumber);
//			}
//
//			String jobId = jobResponse.get("id").asText();
//			uploadservice.uploadCsvToInstance1(accessToken, jobId, chunkData);
//			uploadCompleteService.markUploadCompleteInstance(accessToken, jobId);
//
//			System.out.println("Batch " + batchNumber + " uploaded and job closed successfully.");
//		} catch (Exception e) {
//			System.out.println("Failed to process batch " + batchNumber + ": " + e.getMessage());
//			throw new RuntimeException("Failed to upload batch " + batchNumber, e);
//		}
//	}
//
//	private void uploadBatchInstance2(List<String> batchLines, String header, String accessToken, int batchNumber) {
//		try {
//			StringBuilder chunkDataBuilder = new StringBuilder();
//			chunkDataBuilder.append(header).append("\r\n");
//			for (String dataLine : batchLines) {
//				chunkDataBuilder.append(dataLine).append("\r\n");
//			}
//
//			String chunkData = chunkDataBuilder.toString();
//			System.out
//					.println("Uploading batch for instance 2 " + batchNumber + " with " + batchLines.size() + " lines");
//
//			JsonNode jobResponse = mservice.createJob(accessToken);
//			if (jobResponse == null || !jobResponse.has("id")) {
//				throw new RuntimeException("Failed to create Salesforce job for batch " + batchNumber);
//			}
//
//			String jobId = jobResponse.get("id").asText();
//			uploadservice.uploadCsvToInstance2(accessToken, jobId, chunkData);
//			uploadCompleteService.markUploadCompleteInstance2(accessToken, jobId);
//
//			System.out.println("Batch for instance 2 " + batchNumber + " uploaded and job closed successfully.");
//		} catch (Exception e) {
//			System.out.println("Failed to process batch for instance 2 " + batchNumber + ": " + e.getMessage());
//			throw new RuntimeException("Failed to upload batch for instance 2 " + batchNumber, e);
//		}
//	}
}
