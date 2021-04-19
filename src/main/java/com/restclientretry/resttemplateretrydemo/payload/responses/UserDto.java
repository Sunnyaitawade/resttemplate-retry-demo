package com.restclientretry.resttemplateretrydemo.payload.responses;

import lombok.Data;

@Data
public class UserDto {
    int id;
    String employee_name;
    String profile_image;
    int employee_age;
    double employee_salary;

}
