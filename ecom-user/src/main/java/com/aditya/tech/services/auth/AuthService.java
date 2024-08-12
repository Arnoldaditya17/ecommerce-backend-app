package com.aditya.tech.services.auth;

import com.aditya.tech.dto.SignupRequest;
import com.aditya.tech.dto.UserDto;

public interface  AuthService {
   UserDto createUser(SignupRequest signupRequest);

   Boolean hasUserWithEmail(String email);


}
