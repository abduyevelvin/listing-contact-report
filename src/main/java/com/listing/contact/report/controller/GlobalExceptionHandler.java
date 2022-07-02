package com.listing.contact.report.controller;

import com.listing.contact.report.dto.ErrorDTO;
import com.listing.contact.report.exception.ResourceNotFoundException;
import com.listing.contact.report.exception.WrongDoubleFormatException;
import com.listing.contact.report.exception.WrongParameterException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity handleException(ResourceNotFoundException exception) {
        ErrorDTO errorDTO = ErrorDTO
                .builder()
                .code(exception.getErrorCode())
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongDoubleFormatException.class)
    public ResponseEntity handleException(WrongDoubleFormatException exception) {
        ErrorDTO errorDTO = ErrorDTO
                .builder()
                .code(exception.getErrorCode())
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongParameterException.class)
    public ResponseEntity handleException(WrongParameterException exception) {
        ErrorDTO errorDTO = ErrorDTO
                .builder()
                .code(exception.getErrorCode())
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status,
                                                                          WebRequest request) {
        String message = String.format("Missing required parameter: %s", ex.getParameterName());
        ErrorDTO errorDTO = ErrorDTO
                .builder()
                .code(status.value())
                .message(message)
                .build();

        return new ResponseEntity<>(errorDTO, status);
    }
}
