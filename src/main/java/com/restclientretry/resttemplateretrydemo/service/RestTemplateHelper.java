package com.restclientretry.resttemplateretrydemo.service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.restclientretry.resttemplateretrydemo.exceptions.RestClientErrorHandler;
import com.restclientretry.resttemplateretrydemo.exceptions.RestTemplateException;
import com.restclientretry.resttemplateretrydemo.payload.requests.RequestDetails;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import java.io.IOException;
import java.util.List;

@Component
public class RestTemplateHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateHelper.class);

    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    @Autowired
    public RestTemplateHelper(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
    }
    @Retry(name = "retryService", fallbackMethod = "retryfallback")
    public <T> T getForEntity(RequestDetails request) {
            ResponseEntity <String> response =null;
            if(request.isHeader()){
                HttpEntity <String> entity = new HttpEntity <> (request.getHeaders());
                restTemplate.setErrorHandler(new RestClientErrorHandler());
                 response = restTemplate.exchange(request.getUrl(), HttpMethod.GET, entity, String.class);
            }else{
                 response = restTemplate.getForEntity(request.getUrl(), String.class, request.getUriVariables());
            }
            JavaType javaType = objectMapper.getTypeFactory().constructType(request.getClazz());
            return readValue(response, javaType);
    }

    @Retry(name = "retryService", fallbackMethod = "retryfallback")
    public <T> List<T> getForList(RequestDetails request) {
            ResponseEntity <String> response =null;
            restTemplate.setErrorHandler(new RestClientErrorHandler());
            if(request.isHeader()){
                HttpEntity <String> entity = new HttpEntity <> (request.getHeaders());
                restTemplate.setErrorHandler(new RestClientErrorHandler());
                response = restTemplate.exchange(request.getUrl(), HttpMethod.GET, entity, String.class);
            }else{
                response = restTemplate.getForEntity(request.getUrl(), String.class, request.getUriVariables());
            }
            CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, request.getClazz());
            return readValue(response, collectionType);
    }

    public <T, R> T postForEntity(Class<T> clazz, String url, R body, Object... uriVariables) {
        HttpEntity<R> request = new HttpEntity<>(body);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class, uriVariables);
        JavaType javaType = objectMapper.getTypeFactory().constructType(clazz);
        return readValue(response, javaType);
    }

    public <T, R> T putForEntity(Class<T> clazz, String url, R body, Object... uriVariables) {
        HttpEntity<R> request = new HttpEntity<>(body);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class, uriVariables);
        JavaType javaType = objectMapper.getTypeFactory().constructType(clazz);
        return readValue(response, javaType);
    }

    public void delete(String url, Object... uriVariables) {
        try {
            restTemplate.delete(url, uriVariables);
        } catch (RestClientException exception) {
            LOGGER.info(exception.getMessage());
        }
    }

    private <T> T readValue(ResponseEntity<String> response, JavaType javaType) {
        T result = null;
        if (response.getStatusCode() == HttpStatus.OK ||
                response.getStatusCode() == HttpStatus.CREATED) {
            try {
                result = objectMapper.readValue(response.getBody(), javaType);
            } catch (IOException e) {
                LOGGER.info(e.getMessage());
            }
        } else {
            LOGGER.info("No data found {}", response.getStatusCode());
        }
        return result;
    }


    public String retryfallback(String data, Throwable t) {
        LOGGER.error("Inside retryfallback, cause – {}", t.toString());
        return "Inside retryfallback method. Some error occurred while calling service for user registration";
    }

}