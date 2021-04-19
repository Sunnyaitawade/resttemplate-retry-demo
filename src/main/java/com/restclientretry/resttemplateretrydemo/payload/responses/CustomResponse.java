package com.restclientretry.resttemplateretrydemo.payload.responses;

import lombok.Data;

import java.util.List;

@Data
public class CustomResponse {
    String message;
    String status;
    List<UserDto> data;

}
