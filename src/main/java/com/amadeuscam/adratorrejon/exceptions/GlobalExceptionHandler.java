package com.amadeuscam.adratorrejon.exceptions;


import com.amadeuscam.adratorrejon.dto.ErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorDetails> errorResourceException(ResourceNotFound exption, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exption.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AdraAppException.class)
    public ResponseEntity<ErrorDetails> errorAdraAppException(AdraAppException exption, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exption.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> errorAllExceptions(Exception exption, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exption.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String nameField = ((FieldError) error).getField();
            String messageField = error.getDefaultMessage();
            errors.put(nameField, messageField);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
