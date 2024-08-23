package com.aditya.user.controllers;


import com.aditya.user.dto.*;
import com.aditya.user.exceptions.ErrorResponse;
import com.aditya.user.models.RefreshToken;
import com.aditya.user.models.Token;
import com.aditya.user.models.User;
import com.aditya.user.repositories.TokenRepository;
import com.aditya.user.repositories.UserRepository;
import com.aditya.user.services.auth.AuthService;
import com.aditya.user.services.auth.RefreshTokenService;
import com.aditya.user.utils.JwtUtils;


import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RestController
@Validated
@RequiredArgsConstructor
@Tag(name = "Authentication")
//@Hidden :- isko ham jab use karenge jab hame swagger ui se kisi controller ko ya request ko hide karna hoga
public class AuthController {
    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final UserRepository userRepository;

    private final JwtUtils jwtUtils;
    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String HEADER_STRING = "Authorization";

    public final AuthService authService;
    public final RefreshTokenService refreshTokenService;
    private final TokenRepository tokenRepository;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateAndGetToken(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        try {
            // Authenticate user credentials
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );

            if (authentication.isAuthenticated()) {
                // Load user details
                final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
                Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());

                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();

                    // Generate tokens
                    final String jwt = jwtUtils.generateToken(userDetails.getUsername());
                    RefreshToken refreshToken = refreshTokenService.createRefreshToken(authenticationRequest.getUsername());
                    revokeAllTokenByUser(user);
                    saveUserToken(user, jwt);

                    // Create response DTO
                    JwtResponseDTO jwtResponse = JwtResponseDTO.builder()
                            .userId(user.getId())
                            .role(user.getRole())
                            .accessToken(jwt)
                            .refreshToken(refreshToken.getToken())
                            .build();

                    return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new ErrorResponse("User not found"), HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>(new ErrorResponse("Invalid email or password"), HttpStatus.UNAUTHORIZED);
            }
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new ErrorResponse("Invalid email or password"), HttpStatus.UNAUTHORIZED);
        }
    }

    private void revokeAllTokenByUser(User user) {
        List<Token> validTokenListByUser = tokenRepository.findAllTokenByUser(Math.toIntExact(user.getId()));
        if (!validTokenListByUser.isEmpty()) {
            validTokenListByUser.forEach(token -> {
                token.setLoggedOut(true);
            });
        }
        tokenRepository.saveAll(validTokenListByUser);
    }

    private void saveUserToken(User user, String jwt) {
        Token token = new Token();
        token.setUser(user);
        token.setToken(jwt);
        token.setLoggedOut(false);
        tokenRepository.save(token);
    }


    @PostMapping("/refreshToken")
    public JwtResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtUtils.generateToken(user.getEmail());
                    return JwtResponseDTO.builder()
                            .userId(user.getId())
                            .role(user.getRole())
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenRequestDTO.getRefreshToken()).build();
                }).orElseThrow(() -> new RuntimeException("Refresh Token is not in DB..!!"));
    }


    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@Valid @RequestBody SignupRequest signupRequest) {
        try {
            // Check if the user already exists
            if (authService.hasUserWithEmail(signupRequest.getEmail())) {
                return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
            }

            // Create a new user
            UserDto userDto = authService.createUser(signupRequest);
            return new ResponseEntity<>(userDto, HttpStatus.CREATED);

        } catch (Exception e) {
            // Handle potential exceptions
            return new ResponseEntity<>("An error occurred while creating the user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
