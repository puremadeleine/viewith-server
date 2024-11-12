package com.puremadeleine.viewith.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.puremadeleine.viewith.config.auth.SecurityProperties;
import com.puremadeleine.viewith.exception.ErrorResponse;
import com.puremadeleine.viewith.exception.ViewithErrorCode;
import jakarta.annotation.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Null;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

    public JwtAuthenticationFilter(JwtAuthenticationProvider jwtAuthenticationProvider) {

        setAuthenticationManager(jwtAuthenticationProvider);
        setAuthenticationSuccessHandler(((request, response, authentication) -> {
            if(log.isInfoEnabled()){
                log.info("Found account {} from JWT Token: {}",
                        authentication.getName(),
                        Optional.ofNullable(authentication.getAuthorities())
                                .orElse(List.of())
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.joining(", "))
                );
            }
        }));
        setAuthenticationFailureHandler(((request, response, exception) -> log.info(exception.getMessage())));
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return "VIEWITH_JWT";
    }

    @Nullable
    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return getJwtFromRequest(request);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 제거
        }
        return null;
    }
}