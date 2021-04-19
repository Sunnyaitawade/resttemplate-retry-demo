package com.restclientretry.resttemplateretrydemo.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
public class RestTemplateException  extends RuntimeException {

    private int rawstatus;
    private HttpStatus statusCode;
    private String error;

  public RestTemplateException(int rawstatus, HttpStatus statusCode, String error) {
        super(error);
        this.rawstatus = rawstatus;
        this.statusCode = statusCode;
        this.error = error;
    }

}
