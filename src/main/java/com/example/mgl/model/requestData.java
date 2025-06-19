package com.example.mgl.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class requestData {
	private String operation;
	private String object;
	private String contentType;
	private String lineending;

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getLineending() {
		return lineending;
	}

	public void setLineending(String lineending) {
		this.lineending = lineending;
	}

	public requestData(String operation, String object, String contentType, String lineending) {
		super();
		this.operation = operation;
		this.object = object;
		this.contentType = contentType;
		this.lineending = lineending;
	}

	@Override
	public String toString() {
		return "requestData [operation=" + operation + ", object=" + object + ", contentType=" + contentType
				+ ", lineending=" + lineending + "]";
	}

	public requestData() {
		super();
	}

}
