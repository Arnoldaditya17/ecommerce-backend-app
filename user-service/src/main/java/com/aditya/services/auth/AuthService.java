package com.aditya.services.auth;


import com.aditya.dto.SignupRequest;
import com.aditya.dto.UserDto;

public interface  AuthService {
   UserDto createUser(SignupRequest signupRequest);

   Boolean hasUserWithEmail(String email);


}
