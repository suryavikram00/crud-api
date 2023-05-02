package com.crud.dashboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class ApiResponseValidationError extends ApiResponseError {

	@JsonProperty("object")
	private String object;

	@JsonProperty("field")
	private String field;

	@JsonProperty("rejected_value")
	private Object rejectedValue;

	@JsonProperty("message")
	private String message;

	public ApiResponseValidationError(String object, String message) {
		this.object = object;
		this.message = message;
	}
}
