package com.example.mgl.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

@Service
public class SapMiddlewareService {
	private static final Logger logger = LoggerFactory.getLogger(SapMiddlewareService.class);
	private salesforceBatchService batchService;

//	private static final Map<String, String[]> HEADERS_MAP = Map.of("Account",
//			new String[] { "Account_Email__c", "Application_Number__c", "Building_Code__c", "Building_name__c",
//					"CA_Number__c", "City__c", "Colony__c", "Country_Key__c", "DRS__c", "Flat__c", "Floor__c",
//					"Location__c", "Message_to_meter_reader__c", "Meter_Number__c", "Plot__c", "Postal_Code__c",
//					"Previous_Meter_no__c", "Previous_meter_reading_Date__c", "Region__c", "Road_name__c",
//					"Secondary_Telephone__c", "Society_Name__c", "Street__c", "Street_Line_2__c", "Street_Line_3__c",
//					"Street_Line_4__c", "Street_Line_5__c", "Wing__c", "Account_Group__c", "FirstName__c",
//					"LastName__c", "Premise_SAP__c", "BP_Number__c", "Phone" });
	private static final Map<String, String[]> HEADERS_MAP = Map.of(
		    "Account", new String[] {
		        "Account_Email__c", "Application_Number__c", "Building_Code__c", "Building_name__c", "CA_Number__c",
		        "City__c", "Colony__c", "Country_Key__c", "DRS__c", "Flat__c", "Floor__c", "Location__c",
		        "Message_to_meter_reader__c", "Meter_Number__c", "Plot__c", "Postal_Code__c", "Previous_Meter_no__c",
		        "Previous_meter_reading_Date__c", "Region__c", "Road_name__c", "Secondary_Telephone__c", "Society_Name__c",
		        "Street__c", "Street_Line_2__c", "Street_Line_3__c", "Street_Line_4__c", "Street_Line_5__c", "Wing__c",
		        "Account_Group__c", "FirstName__c", "LastName__c", "Premise_SAP__c", "BP_Number__c", "Phone"
		    },

		    "Connection__c", new String[] {
		        "Connection_Code__c", "Date_From__c", "Date_To__c", "House_Number__c", "District__c",
		        "MC_Street__c", "MC_City1__c", "Object_Category__c", "Nation__c", "Postal_Code__c"
		    },

		    "Premise__c", new String[] {
		        "Connection_Name__c", "Floor__c", "Premise_Code__c", "Room_Nummber__c", "Street_5__c", "Type_of_Premise__c"
		    },
		    "Installation__c", new String[] {
		    		"Meter_Reading_Unit__c","Installation_Type__c","Installation_Code__c","Industry_System__c",
		    		"Division__c","Billing_Class__c","Valid_To__c","Valid_From__c","Temperature_Area__c",
		    		"Rate_Category__c","Premise_Name__c"
		    },
		    "ServiceContract", new String[] {
		    		"Contract_Account_Code__c","Business_Partner_Name__c","Contract_Account_Name__c","Company_Code_Group__c"
		    },
		    "Billing__c", new String[] {
		    		"Billing_Code__c","Business_Partner_SAP__c","Contract_Account_SAP__c","Company_Code__c","Contract_SAP__c",
		    		"Billing_Trans__c","Division__c","Document_Type__c","MR_Unit__c","Portion__c","Prev_doc_No__c"
		    },
		    "Security_Deposit__c",new String[] {
		    		"Security_Deposit_Code__c","Cash_Deposit_Payment__c","Request_Amount__c","Business_Partner__c",
		    		"Status__c","Start_Date__c","Return_Date__c","Non_Cash__c","Description__c","Currency__c","Contract_Account_SAP__c","Contract_SAP__c"	
		    }
		    
		);
	

	public SapMiddlewareService(salesforceBatchService batchService) {
		this.batchService = batchService;
	}

	public String processAndUpload(String objectName, String rawBody, String externalIdFieldName) {
		logger.info("Received data for object", objectName);

		String[] headers = HEADERS_MAP.get(objectName);
		if (headers == null) {
			String error = "Unsupported Salesforce object: " + objectName;
			logger.error(error);
			return error;
		}

		String[] lines = rawBody.split("\\r?\\n");
		StringBuilder csvBuilder = new StringBuilder(String.join(",", headers)).append("\n");
		List<String> errors = new ArrayList<>();
		int lineNum = 1;

		for (String line : lines) {
			String[] values = line.split(",", -1);
			if (values.length != headers.length) {
				String error = String.format("Line %d error: Expected %d values, got %d - Data: [%s]", lineNum,
						headers.length, values.length, line);
				errors.add(error);
				logger.warn(error);
			} else {
				csvBuilder.append(String.join(",", values)).append("\n");
			}
			lineNum++;
		}

		try {
			String finalCsv = csvBuilder.toString().trim();
			logger.info("CSV validated successfully. Initiating upload to Salesforce.");
			JsonNode response = batchService.handleInstance2BatchUpload(objectName, finalCsv, externalIdFieldName);
			logger.info("Salesforce response:", response.toPrettyString());
			return response.toPrettyString();
		} catch (Exception e) {
			logger.error("Exception during Salesforce upload", e.getMessage(), e);
			return "Error during Salesforce upload: " + e.getMessage();
		}
	}
} 

