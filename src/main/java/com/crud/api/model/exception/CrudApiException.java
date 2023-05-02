package com.crud.api.model.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CrudApiException extends RuntimeException {
	private static final long serialVersionUID = 1L;

    public CrudApiException(String message) {
        super(message);
    }
}
