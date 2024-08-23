package com.aditya.user.dto;
import com.aditya.user.enums.UserRole;
import lombok.Data;


@Data
public class UserDto {

    private Long id;

    private String email;

    private String name;

    private UserRole userRole;
}
