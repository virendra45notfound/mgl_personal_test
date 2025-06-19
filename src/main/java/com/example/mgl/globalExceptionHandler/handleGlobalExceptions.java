package com.example.mgl.globalExceptionHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class handleGlobalExceptions{

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
		return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Runtime error occurred", ex.getMessage());
	}

	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<Object> handleHttpClientError(HttpClientErrorException ex, WebRequest request) {
		return buildResponseEntity(ex.getStatusCode(), "Salesforce HTTP error", ex.getResponseBodyAsString());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
		return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", ex.getMessage());
	}

	private ResponseEntity<Object> buildResponseEntity(HttpStatusCode httpStatusCode, String error, String message) {

		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("current_date", LocalDate.now());
		body.put("status", httpStatusCode.value());
		body.put("error", error);
		body.put("message", message);
		return new ResponseEntity<>(body, httpStatusCode);
	}
}
