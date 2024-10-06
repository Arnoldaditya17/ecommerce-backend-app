package com.aditya.user.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDto {
    private String name;
    private String email;
    private String password;
}
