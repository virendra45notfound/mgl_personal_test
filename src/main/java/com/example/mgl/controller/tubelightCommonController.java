package com.example.mgl.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mgl.service.tubelight.fetchBalanceDetailsService;
import com.example.mgl.service.tubelight.fetchDeliveryStatusService;
import com.example.mgl.service.tubelight.sendBulkSmsService;
import com.example.mgl.service.tubelight.sendFlashSmsService;
import com.example.mgl.service.tubelight.sendPlainSmsService;
import com.example.mgl.service.tubelight.sendUnicodeFlashSmsService;
import com.example.mgl.service.tubelight.sendUnicodeSmsService;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("tubelight")
public class tubelightCommonController {

	private sendPlainSmsService tubelightPlainService;
	private sendFlashSmsService tubelightFlashService;
	private sendBulkSmsService tubelightBulkService;
	private sendUnicodeSmsService tubelightUnicodeService;
	private sendUnicodeFlashSmsService tubelightUnicodeFlashService;
	private fetchBalanceDetailsService tubelightBalanceService;
	private fetchDeliveryStatusService tubelightDeliveryStatusService;
	
	public tubelightCommonController(sendPlainSmsService tubelightPlainService,
														   sendFlashSmsService tubelightFlashService,
														   sendBulkSmsService tubelightBulkService,
														   sendUnicodeSmsService tubelightUnicodeService,
														   sendUnicodeFlashSmsService tubelightUnicodeFlashService,
														   fetchBalanceDetailsService tubelightBalanceService,
														   fetchDeliveryStatusService tubelightDeliveryStatusService
														   ) {
		this.tubelightPlainService = tubelightPlainService;
		this.tubelightFlashService = tubelightFlashService;
		this.tubelightBulkService = tubelightBulkService;
		this.tubelightUnicodeService = tubelightUnicodeService;
		this.tubelightUnicodeFlashService = tubelightUnicodeFlashService;
		this.tubelightBalanceService = tubelightBalanceService;
		this.tubelightDeliveryStatusService = tubelightDeliveryStatusService;
	}
	
	@GetMapping("/send-sms")
	public String sendSMS(@RequestBody String message) throws IOException, InterruptedException {
		String response = tubelightPlainService.sendTextMessage(message);
		return response;
		
	}
	@GetMapping("/send-flash-sms")
	public String sendFlashSMS(@RequestBody String message) throws IOException, InterruptedException {
		String response = tubelightFlashService.sendFlashMessage(message);
		return response;
		
	}
	@GetMapping("/send-unicode-sms")
	public String sendUnicodeSMS(@RequestBody String message) throws IOException, InterruptedException {
		String response = tubelightUnicodeService.sendUnicodeMessage(message);
		return response;
		
	}
	@GetMapping("/send-unicode-flash-sms")
	public String sendUnicodeFlashSMS(@RequestBody String message) throws IOException, InterruptedException {
		String response = tubelightUnicodeFlashService.sendUnicodeFlashMessage(message);
		return response;
		
	}
	@PostMapping("/send-bulk-sms")
	public String sendBulkSMS(@RequestBody JsonNode message) throws IOException, InterruptedException {
		String response = tubelightBulkService.sendBulkMessage(message);
		return response;
		
	}
	@GetMapping("/balance")
	public String checkBalance() throws IOException, InterruptedException {
		String response = tubelightBalanceService.checkBalance();
		return response;
	}
	@GetMapping("/delivery-status")
	public String checkDeliveryStatus(@RequestBody String messageId) throws IOException, InterruptedException {
		String response = tubelightDeliveryStatusService.fetchDeliveryStatus(messageId);
		return response;
		
	}

}
