package com.puremadeleine.viewith.auth;

import com.puremadeleine.viewith.service.JwtService;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Slf4j
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
        if (isValid) {
            log.info("[Viewith-Error] Error Token: " + token);
            throw new AuthenticationCredentialsNotFoundException("Invalid Access Token");
        }

        return jwtService.getAuthentication(token);
    }
}