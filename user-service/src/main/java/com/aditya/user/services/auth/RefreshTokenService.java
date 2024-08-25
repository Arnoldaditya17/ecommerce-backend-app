package com.aditya.user.services.auth;



import com.aditya.user.models.RefreshToken;
import com.aditya.user.repositories.RefreshTokenRepository;
import com.aditya.user.repositories.UserRepository;
import com.aditya.user.models.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;

import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;
    @Autowired
    UserRepository userRepository;

//@Cacheable(value = "refreshToken", key = "#username")
    public RefreshToken createRefreshToken(String username) {
        Optional<User> userOptional = userRepository.findFirstByEmail(username);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        User user = userOptional.get();
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000)) // set expiry of refresh token to 10 minutes
                .build();

        return refreshTokenRepository.save(refreshToken);
    }


    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);

    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;
    }


}