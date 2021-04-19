package com.restclientretry.resttemplateretrydemo.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Data
public class ErrorResponse {
    private String timestamp;

    /** HTTP Status Code */
    private int status;

    /** HTTP Reason phrase */
    private String error;

    /** A message that describe the error thrown when calling the downstream API */
    private String message;

    /** Downstream API name that has been called by this application */
    private String api;

    /** URI that has been called */
    private String path;

    public ErrorResponse(RestTemplateException ex, String path) {
        this.timestamp = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now());
        this.status = ex.getStatusCode().value();
        this.error = ex.getStatusCode().getReasonPhrase();
        this.message = ex.getError();
        this.api = path;
        this.path = path;
    }

    public ErrorResponse(IOException ex, String path) {
        this.timestamp = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now());
        this.status = 500;
        this.error = ex.getStackTrace().toString();
        this.message = ex.getMessage();
        this.api = path;
        this.path = path;
    }
}
