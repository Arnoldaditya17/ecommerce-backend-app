package com.aditya.user.services.auth;



import com.aditya.user.models.RefreshTokenEntity;
import com.aditya.user.repositories.RefreshTokenRepository;
import com.aditya.user.repositories.UserRepository;
import com.aditya.user.models.UserEntity;
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
    public RefreshTokenEntity createRefreshToken(String username) {
        Optional<UserEntity> userOptional = userRepository.findFirstByEmail(username);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        UserEntity user = userOptional.get();
        RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000)) // set expiry of refresh token to 10 minutes
                .build();

        return refreshTokenRepository.save(refreshToken);
    }


    public Optional<RefreshTokenEntity> findByToken(String token){
        return refreshTokenRepository.findByToken(token);

    }

    public RefreshTokenEntity verifyExpiration(RefreshTokenEntity token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;
    }


}
