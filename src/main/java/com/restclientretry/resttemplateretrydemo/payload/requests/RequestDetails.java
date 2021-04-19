package com.restclientretry.resttemplateretrydemo.payload.requests;

import jdk.nashorn.internal.runtime.options.Option;
import lombok.Data;
import org.springframework.http.HttpHeaders;

import java.util.Optional;

@Data
public class RequestDetails <T, R>{

    Class<T> clazz;
    String url;
    R body;
    Object[]  uriVariables;
    boolean isHeader;
    HttpHeaders headers;

}
