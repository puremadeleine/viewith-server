package com.puremadeleine.viewith.auth;

import com.puremadeleine.viewith.service.JwtService;
import jakarta.annotation.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Objects;

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

        if (!Objects.isNull(token) || !jwtService.validateAccessToken(token)) {
            throw new BadCredentialsException("Invalid token");
        }

        return jwtService.getAuthentication(token);
    }

//    @Override
//    public boolean supports(Class<?> authentication) {
//        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
//    }
}