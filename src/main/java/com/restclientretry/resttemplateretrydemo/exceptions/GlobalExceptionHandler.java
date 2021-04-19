package com.restclientretry.resttemplateretrydemo.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = RestTemplateException.class)
    ResponseEntity<ErrorResponse> handleMyRestTemplateException(RestTemplateException ex, HttpServletRequest request) {
        LOGGER.error("An error happened while calling {} Downstream API: {}", request.getRequestURI(), ex.toString());
        return new ResponseEntity<>(new ErrorResponse(ex, request.getRequestURI()), ex.getStatusCode());
    }

    @ExceptionHandler(IOException.class)
    ResponseEntity<ErrorResponse>  handleIOException(IOException ex, HttpServletRequest request){
        LOGGER.error("IOException handler executed");
        return new ResponseEntity<>(new ErrorResponse(ex, request.getRequestURI()), HttpStatus.NOT_FOUND);
    }
}
