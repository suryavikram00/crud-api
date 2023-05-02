package com.crud.api.model;

import com.crud.dashboard.constants.StatusEnum;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CrudDashboardResponse<T> {

	@JsonProperty("status")
	private StatusEnum status;

	@JsonProperty("time_stamp")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;

	@JsonProperty("message")
	private String message;

	@JsonProperty("debug_message")
	private String debugMessage;

	@JsonProperty("error_list")
	private List<ApiResponseValidationError> errorList;

	@JsonProperty("object")
	private T object;

	@JsonProperty("objectList")
	private List<T> objectList;

	private CrudDashboardResponse() {
		timestamp = LocalDateTime.now();
	}

	public CrudDashboardResponse(StatusEnum status) {
		this();
		this.status = status;
	}

	public CrudDashboardResponse<T> addMessage(String message) {
		this.message = message;
		return this;
	}

	public CrudDashboardResponse<T> addDebugMessage(Throwable debugMessage) {
		this.debugMessage = debugMessage.getLocalizedMessage();
		return this;
	}


}
