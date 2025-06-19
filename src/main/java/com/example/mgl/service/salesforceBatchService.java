package com.example.mgl.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class salesforceBatchService {
	@Autowired
	private salesforceCreateDataService mservice;
	private salesforceTokenService tokenservice;
	private salesforceUploadDataService uploadservice;
	private salesforceUploadCompleteService completeservice;
	private final ObjectMapper objectMapper = new ObjectMapper();

	public salesforceBatchService(salesforceCreateDataService mservice, salesforceTokenService tokenservice,
			salesforceUploadDataService uploadservice, salesforceUploadCompleteService completeservice) {
		super();
		this.mservice = mservice;
		this.tokenservice = tokenservice;
		this.uploadservice = uploadservice;
		this.completeservice = completeservice;
	}

	public JsonNode handleInstance2BatchUpload(String objectName, String csvData,String externalIdFieldName) throws IOException, InterruptedException {
		JsonNode token = tokenservice.getAccessToken();
		String accessToken = token.get("access_token").asText();

		JsonNode job = mservice.createJob2(accessToken, objectName,externalIdFieldName);
		String jobId = job.get("id").asText();

		uploadservice.uploadCsvToInstance2(accessToken, jobId, csvData);
		return completeservice.markUploadCompleteInstance(accessToken, jobId);
	}
}