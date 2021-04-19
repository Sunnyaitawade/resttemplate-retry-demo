package com.restclientretry.resttemplateretrydemo.controller;

import com.restclientretry.resttemplateretrydemo.payload.requests.RequestDetails;
import com.restclientretry.resttemplateretrydemo.payload.responses.CustomResponse;
import com.restclientretry.resttemplateretrydemo.payload.responses.UserDto;
import com.restclientretry.resttemplateretrydemo.service.RestTemplateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.SchemaOutputResolver;

@RestController
public class TestController {
    @Autowired
    private RestTemplateHelper restTemplateHelper;


    @GetMapping(value="customer/get",produces={"application/json"})
    public CustomResponse getData(){
        Object[] arguments = {
                new Integer(2)


        };
        RequestDetails request = new RequestDetails();
        request.setClazz(CustomResponse.class);
        request.setUrl("http://dummy.restapiexample.com/api/v1/employees");
        request.setUriVariables(arguments);
        request.setHeader(true);
        request.setHeaders(null);
        CustomResponse customResponse = restTemplateHelper.getForEntity(request);
        return customResponse;
    }
}
