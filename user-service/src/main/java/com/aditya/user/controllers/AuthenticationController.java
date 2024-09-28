package com.aditya.user.controllers;

import com.aditya.user.dtos.LoginResponse;
import com.aditya.user.dtos.LoginUserDto;
import com.aditya.user.dtos.RefreshTokenRequest;
import com.aditya.user.dtos.RegisterUserDto;
import com.aditya.user.models.RefreshToken;
import com.aditya.user.models.User;
import com.aditya.user.services.auth.AuthenticationService;
import com.aditya.user.services.auth.JwtService;
import com.aditya.user.services.auth.RefreshTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    private final RefreshTokenService refreshTokenService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, RefreshTokenService refreshTokenService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(authenticatedUser.getEmail());


        LoginResponse loginResponse = new LoginResponse()
                .setToken(jwtToken)
                .setRefreshToken(refreshToken.getRefreshToken())
                .setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/refreshToken")
    public LoginResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
       return refreshTokenService.findByRefreshToken(refreshTokenRequest.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                   String accessToken = jwtService.generateToken(user);

                   LoginResponse loginResponse = new LoginResponse();
                   loginResponse.setAccessToken(accessToken);
                   loginResponse.setExpiresIn(jwtService.getExpirationTime());
                   loginResponse.setRefreshToken(refreshTokenRequest.getRefreshToken());
                   return loginResponse;
                }).orElseThrow(() -> new RuntimeException("Refresh token not found in Database!"));
    }
}
