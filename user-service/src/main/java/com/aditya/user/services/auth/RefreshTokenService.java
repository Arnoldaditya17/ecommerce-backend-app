package com.aditya.user.services.auth;


import com.aditya.user.models.RefreshToken;
import com.aditya.user.repositories.RefreshTokenRepository;
import com.aditya.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class RefreshTokenService {

    @Value("${app.jwtRefreshExpirationMs}")
    private long refreshTokenDurationMs;

    private static final Logger logger = Logger.getLogger(RefreshTokenService.class.getName());

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    public RefreshToken createRefreshToken(String email) {
        return userRepository.findByEmail(email).map(user -> {
            // Check if a refresh token already exists for the user
            Optional<RefreshToken> existingToken = refreshTokenRepository.findByUser(user);
            existingToken.ifPresent(refreshTokenRepository::delete);

            // Create a new refresh token
            RefreshToken refreshToken = new RefreshToken();
            refreshToken.setUser(user);
            refreshToken.setRefreshToken(UUID.randomUUID().toString());
            refreshToken.setExpiresAt(Instant.now().plusMillis(refreshTokenDurationMs));
            return refreshTokenRepository.save(refreshToken);
        }).orElseThrow(() -> {
            logger.warning("User not found with email: " + email);
            return new RuntimeException("User not found");
        });
    }

    public Optional<RefreshToken> findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpiresAt().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token expired");
        }
        return refreshToken;

    }


}
