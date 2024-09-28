package com.aditya.user.services.auth;


import com.aditya.user.models.RefreshToken;
import com.aditya.user.repositories.RefreshTokenRepository;
import com.aditya.user.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    public RefreshToken createRefreshToken(String email) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userRepository.findByEmail(email).get());
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setExpiresAt(Instant.now().plusMillis(999900000));
       return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken refreshToken)
    {
        if(refreshToken.getExpiresAt().compareTo(Instant.now())<0)
        {
            refreshTokenRepository.delete(refreshToken);
            throw  new RuntimeException("Refresh token expired");
        }
        return refreshToken;

    }


}
