package com.aditya.tech.config;

import com.aditya.tech.models.Token;
import com.aditya.tech.repositories.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLogoutHandler implements LogoutHandler {
    private final TokenRepository tokenRepository;

    public CustomLogoutHandler(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        String authHeader = request.getHeader("Authorization");

        // Check if authHeader is null or doesn't start with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        String token = authHeader.substring(7);

        // Check if the token is empty or null
        if (token.isEmpty()) {
            return;
        }

        Token tokenStored = tokenRepository.findByToken(token).orElse(null);
        if (tokenStored != null) {
            tokenStored.setLoggedOut(true);
            tokenRepository.save(tokenStored);
        }
    }
}
