package com.crud.api.exceptionhandler;

import com.crud.api.constants.StatusEnum;
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

import com.crud.api.model.CrudApiResponseValidationError;
import com.crud.api.model.CrudApiResponse;
import com.crud.api.model.exception.CrudApiException;


@ControllerAdvice
public class CrudApiExceptionHandler {

    @ExceptionHandler(value = CrudApiException.class)
    public ResponseEntity<CrudApiResponse<String>> magnumExceptionHandler(CrudApiException exception, WebRequest request) {
        String message = "Magnum Runtime Exception";
        return buildResponseEntity(new CrudApiResponse<String>(StatusEnum.FAILURE).addMessage(message).addDebugMessage(exception));
    }

    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<CrudApiResponse<String>> constraintViolationExceptionHandler(
            SQLIntegrityConstraintViolationException ex, WebRequest request) {
        String message = "Data Integrity Violation";
        CrudApiResponse<String> crudResponse = new CrudApiResponse<>(StatusEnum.FAILURE);
        crudResponse.setMessage("Data is not valid");
        crudResponse.setDebugMessage(ex.getLocalizedMessage());

        return buildResponseEntity(new CrudApiResponse<String>(StatusEnum.FAILURE)
                .addMessage(ex.getMessage() != null ? ex.getMessage() : message).addDebugMessage(ex));
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<CrudApiResponse<String>> constraintViolationExceptionHandler(ConstraintViolationException ex,
            WebRequest request) {
        StringBuffer message = new StringBuffer("Data is not valid");
        CrudApiResponse<String> magnumResponse = new CrudApiResponse<>(StatusEnum.FAILURE);

        List<CrudApiResponseValidationError> validationErrorList = ex.getConstraintViolations().stream().map(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getMessage();
            message.append(" || " + fieldName + ":" + errorMessage);
            return new CrudApiResponseValidationError(fieldName, errorMessage);
        }).collect(Collectors.toList());

        magnumResponse.setMessage(message.toString());
        magnumResponse.setDebugMessage(message.toString());

        magnumResponse.setErrorList(validationErrorList);

        return buildResponseEntity(new CrudApiResponse<String>(StatusEnum.FAILURE)
                .addMessage(ex.getMessage() != null ? ex.getMessage() : message.toString()).addDebugMessage(ex));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<CrudApiResponse<String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {

        CrudApiResponse<String> magnumResponse = new CrudApiResponse<>(StatusEnum.FAILURE);

        StringBuffer message = new StringBuffer("Data is not valid");

        magnumResponse.setErrorList(ex.getBindingResult().getAllErrors().stream().map((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            message.append(" || " + fieldName + ":" + errorMessage);
            return new CrudApiResponseValidationError(fieldName, errorMessage);
        }).collect(Collectors.toList()));

        magnumResponse.setMessage(message.toString());
        magnumResponse.setDebugMessage(message.toString());

        return buildResponseEntity(magnumResponse);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<CrudApiResponse<String>> genericExceptionHandler(Exception exception, WebRequest request) {
        String message = "Something went wrong,Please contact magnum team";
        return buildResponseEntity(new CrudApiResponse<String>(StatusEnum.FAILURE).addMessage(message).addDebugMessage(exception));
    }

    private ResponseEntity<CrudApiResponse<String>> buildResponseEntity(CrudApiResponse<String> magnumResponse) {
        return new ResponseEntity<>(magnumResponse, HttpStatus.ACCEPTED);
    }

}
