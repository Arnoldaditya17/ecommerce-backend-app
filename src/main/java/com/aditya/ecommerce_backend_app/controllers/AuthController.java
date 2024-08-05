package com.aditya.ecommerce_backend_app.controllers;
import com.aditya.ecommerce_backend_app.dto.*;
import com.aditya.ecommerce_backend_app.exceptions.ErrorResponse;
import com.aditya.ecommerce_backend_app.models.RefreshToken;
import com.aditya.ecommerce_backend_app.models.User;
import com.aditya.ecommerce_backend_app.repositories.UserRepository;
import com.aditya.ecommerce_backend_app.services.auth.AuthService;
import com.aditya.ecommerce_backend_app.services.auth.RefreshTokenService;
import com.aditya.ecommerce_backend_app.utils.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.json.JSONException;
import org.json.JSONObject;
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
import java.io.IOException;
import java.util.Optional;

@RestController
@Validated
@RequiredArgsConstructor
public class AuthController {
    private  final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final UserRepository userRepository;

    private final JwtUtils jwtUtils;
    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String HEADER_STRING = "Authorization";

    public final AuthService authService;
    public final RefreshTokenService refreshTokenService;




//    @PostMapping("/authenticate")
//    public void createUser(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws IOException, JSONException {
//        try {
//            // Authenticate user credentials
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
//            );
//        } catch (BadCredentialsException e) {
//            // Handle invalid credentials
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().write(new JSONObject().put("error", "Invalid email or password").toString());
//            return;
//        }
//
//        // Load user details and find user by email
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
//        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
//
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            // Generate JWT token
//            final String jwt = jwtUtils.generateToken(userDetails.getUsername());
//
//            // Create JSON response
//            JSONObject jsonResponse = new JSONObject();
//            jsonResponse.put("userId", user.getId());
//            jsonResponse.put("role", user.getRole());
//            jsonResponse.put("Access-Token", jwt);
//
//            // Write JSON response
//            response.setStatus(HttpServletResponse.SC_OK);
//            response.setContentType("application/json");
//            response.getWriter().write(jsonResponse.toString());
//            response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
//        } else {
//            // Handle case where user is not found
//            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//            response.getWriter().write(new JSONObject().put("error", "User not found").toString());
//        }
//    }

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


    @PostMapping("/refreshToken")
    public JwtResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtUtils.generateToken(user.getEmail());
                    return JwtResponseDTO.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenRequestDTO.getRefreshToken()).build();
                }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
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
