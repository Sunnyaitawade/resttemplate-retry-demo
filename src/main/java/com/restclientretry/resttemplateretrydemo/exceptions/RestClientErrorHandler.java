package com.restclientretry.resttemplateretrydemo.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class RestClientErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        return new DefaultResponseErrorHandler().hasError(clientHttpResponse);
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
//        if (clientHttpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
//            // handle 5xx errors
//            // raw http status code e.g `500`
//            System.out.println(clientHttpResponse.getRawStatusCode());
//            // http status code e.g. `500 INTERNAL_SERVER_ERROR`
//            System.out.println(clientHttpResponse.getStatusCode());
//            throw new RestTemplateException(clientHttpResponse.getRawStatusCode(), clientHttpResponse.getStatusCode(), clientHttpResponse.getBody());
//
//        } else
            if (clientHttpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR || clientHttpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientHttpResponse.getBody()))) {
                String httpBodyResponse = reader.lines().collect(Collectors.joining(""));

                // TODO deserialize (could be JSON, XML, whatever...) httpBodyResponse to a POJO that matches the error structure for that specific API, then extract the error message.
                // Here the whole response will be treated as the error message, you probably don't want that.
                String errorMessage = httpBodyResponse;

                // handle 4xx errors
                // raw http status code e.g `404`
                //System.out.println(clientHttpResponse.getRawStatusCode());

                // http status code e.g. `404 NOT_FOUND`
               // System.out.println(clientHttpResponse.getStatusCode());

                // get response body
                //System.out.println(clientHttpResponse.getBody());

                // get http headers
                HttpHeaders headers = clientHttpResponse.getHeaders();
                //System.out.println(headers.get("Content-Type"));
               // System.out.println(headers.get("Server"));
                throw new RestTemplateException(clientHttpResponse.getRawStatusCode(), clientHttpResponse.getStatusCode(), errorMessage);
            }


        }
    }

}
