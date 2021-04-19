package com.restclientretry.resttemplateretrydemo;

import com.restclientretry.resttemplateretrydemo.payload.responses.UserDto;
import com.restclientretry.resttemplateretrydemo.service.RestTemplateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ResttemplateRetryDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResttemplateRetryDemoApplication.class, args);
    }
}
