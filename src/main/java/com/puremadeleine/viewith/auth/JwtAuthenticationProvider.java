package com.puremadeleine.viewith.auth;

import com.puremadeleine.viewith.service.JwtService;
import jakarta.annotation.Nullable;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class JwtAuthenticationProvider implements AuthenticationManager {

    private final JwtService jwtService;

    public JwtAuthenticationProvider(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Nullable
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getCredentials();
        if (isNull(token) || token.isEmpty()) {
            return null;
        }

        boolean isValid = jwtService.validateAccessToken(token);
        if (!isValid) {
            throw new AuthenticationCredentialsNotFoundException("Invalid Access Token");
        }

        return jwtService.getAuthentication(token);
    }
}