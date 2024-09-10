package com.aditya.user.filters;


import com.aditya.user.services.jwt.UserDetailsServiceImpl;
import com.aditya.user.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {



    @Autowired
    private JwtUtils jwtUtils;


    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        try {
            // Check if Authorization header is present and starts with "Bearer"
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                username = jwtUtils.extractUsername(token);
            }

            // If username is extracted and no authentication is present in the context
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Validate the token
                if (jwtUtils.validateToken(token, userDetails)) {
                    // Set the authentication in the security context
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    // Token is invalid
                    throw new BadCredentialsException("Invalid JWT token");
                }
            }

        } catch (Exception ex) {
            // Set the response status to 401 Unauthorized for any JWT-related exception
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"" + ex.getMessage() + "\", \"status\": 401}");
            return; // Prevent further processing of the request
        }

        // Proceed with the next filter in the chain
        filterChain.doFilter(request, response);
    }


}
