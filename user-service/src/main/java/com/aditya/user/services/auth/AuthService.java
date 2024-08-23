package com.aditya.user.services.auth;


import com.aditya.user.dto.SignupRequest;
import com.aditya.user.dto.UserDto;

public interface  AuthService {
   UserDto createUser(SignupRequest signupRequest);

   Boolean hasUserWithEmail(String email);


}
