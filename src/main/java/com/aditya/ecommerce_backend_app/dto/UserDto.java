package com.aditya.ecommerce_backend_app.dto;

import com.aditya.ecommerce_backend_app.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {

    private Long id;

    private String email;

    private String name;

    private UserRole userRole;
}
