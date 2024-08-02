package com.aditya.ecommerce_backend_app.services.auth;

import com.aditya.ecommerce_backend_app.dto.SignupRequest;
import com.aditya.ecommerce_backend_app.dto.UserDto;

public interface  AuthService {
   UserDto createUser(SignupRequest signupRequest);

   Boolean hasUserWithEmail(String email);


}
