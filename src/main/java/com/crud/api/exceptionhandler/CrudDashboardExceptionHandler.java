package com.crud.api.exceptionhandler;

import com.crud.dashboard.constants.StatusEnum;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.crud.dashboard.model.ApiResponseValidationError;
import com.crud.dashboard.model.CrudDashboardResponse;
import com.crud.dashboard.model.exception.CrudDashboardException;

@ControllerAdvice
public class CrudDashboardExceptionHandler {

    @ExceptionHandler(value = CrudDashboardException.class)
    public ResponseEntity<CrudDashboardResponse<String>> magnumExceptionHandler(CrudDashboardException exception, WebRequest request) {
        String message = "Magnum Runtime Exception";
        return buildResponseEntity(new CrudDashboardResponse<String>(StatusEnum.FAILURE).addMessage(message).addDebugMessage(exception));
    }

    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<CrudDashboardResponse<String>> constraintViolationExceptionHandler(
            SQLIntegrityConstraintViolationException ex, WebRequest request) {
        String message = "Data Integrity Violation";
        CrudDashboardResponse<String> magnumResponse = new CrudDashboardResponse<>(StatusEnum.FAILURE);
        magnumResponse.setMessage("Data is not valid");
        magnumResponse.setDebugMessage(ex.getLocalizedMessage());

        return buildResponseEntity(new CrudDashboardResponse<String>(StatusEnum.FAILURE)
                .addMessage(ex.getMessage() != null ? ex.getMessage() : message).addDebugMessage(ex));
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<CrudDashboardResponse<String>> constraintViolationExceptionHandler(ConstraintViolationException ex,
            WebRequest request) {
        StringBuffer message = new StringBuffer("Data is not valid");
        CrudDashboardResponse<String> magnumResponse = new CrudDashboardResponse<>(StatusEnum.FAILURE);

        List<ApiResponseValidationError> validationErrorList = ex.getConstraintViolations().stream().map(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getMessage();
            message.append(" || " + fieldName + ":" + errorMessage);
            return new ApiResponseValidationError(fieldName, errorMessage);
        }).collect(Collectors.toList());

        magnumResponse.setMessage(message.toString());
        magnumResponse.setDebugMessage(message.toString());

        magnumResponse.setErrorList(validationErrorList);

        return buildResponseEntity(new CrudDashboardResponse<String>(StatusEnum.FAILURE)
                .addMessage(ex.getMessage() != null ? ex.getMessage() : message.toString()).addDebugMessage(ex));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<CrudDashboardResponse<String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {

        CrudDashboardResponse<String> magnumResponse = new CrudDashboardResponse<>(StatusEnum.FAILURE);

        StringBuffer message = new StringBuffer("Data is not valid");

        magnumResponse.setErrorList(ex.getBindingResult().getAllErrors().stream().map((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            message.append(" || " + fieldName + ":" + errorMessage);
            return new ApiResponseValidationError(fieldName, errorMessage);
        }).collect(Collectors.toList()));

        magnumResponse.setMessage(message.toString());
        magnumResponse.setDebugMessage(message.toString());

        return buildResponseEntity(magnumResponse);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<CrudDashboardResponse<String>> genericExceptionHandler(Exception exception, WebRequest request) {
        String message = "Something went wrong,Please contact magnum team";
        return buildResponseEntity(new CrudDashboardResponse<String>(StatusEnum.FAILURE).addMessage(message).addDebugMessage(exception));
    }

    private ResponseEntity<CrudDashboardResponse<String>> buildResponseEntity(CrudDashboardResponse<String> magnumResponse) {
        return new ResponseEntity<>(magnumResponse, HttpStatus.BAD_REQUEST);
    }

}
