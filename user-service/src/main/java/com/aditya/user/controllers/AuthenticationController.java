package com.aditya.user.controllers;

import com.aditya.user.constants.StringConstants;
import com.aditya.user.dtos.*;
import com.aditya.user.models.RefreshToken;
import com.aditya.user.models.User;
import com.aditya.user.repositories.UserRepository;
import com.aditya.user.services.mail.EmailService;
import com.aditya.user.services.auth.AuthenticationService;
import com.aditya.user.services.auth.JwtService;
import com.aditya.user.services.auth.RefreshTokenService;
import com.aditya.user.services.mail.OtpGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@RequestMapping("/api/v1/auth")
@RestController
public class AuthenticationController {

    private final ConcurrentHashMap<String, String> otpStore = new ConcurrentHashMap<>();

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    private final RefreshTokenService refreshTokenService;

    private final UserRepository userRepository;

    @Autowired
    EmailService emailService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, RefreshTokenService refreshTokenService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.refreshTokenService = refreshTokenService;
        this.userRepository = userRepository;
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

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found.");
        }
        String otp = OtpGenerator.generateOtp();
        otpStore.put(email, otp);
        emailService.sendForgotPasswordMail(StringConstants.EMAIL_FROM, email, otp);
        return ResponseEntity.status(HttpStatus.OK).body("Mail sent successfully");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<Boolean> verifyOtp(@RequestParam("email") String email,
                                             @RequestParam("otp") String userProvidedOtp) {
        boolean isOTPVerified = isVerified(email, userProvidedOtp);
        otpStore.remove(email);
        return ResponseEntity.ok(isOTPVerified);
    }

    public boolean isVerified(String email, String userProvidedOtp) {

        if (!otpStore.containsKey(email)) {
            return false;
        }
        String storedOtp = otpStore.get(email);
        return storedOtp.equals(userProvidedOtp);
    }


}
