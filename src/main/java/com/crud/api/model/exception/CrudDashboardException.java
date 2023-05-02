package com.crud.api.model.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CrudDashboardException extends RuntimeException {
	private static final long serialVersionUID = 1L;

    public CrudDashboardException(String message) {
        super(message);
    }
}
