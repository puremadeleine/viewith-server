package com.puremadeleine.viewith.auth;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import java.security.Principal;
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
            if (log.isInfoEnabled()) {
                log.info("Found account {} from JWT Token: {}",
                        Optional.ofNullable(authentication)
                                .map(Principal::getName)
                                .orElse(null),
                        Optional.ofNullable(authentication)
                                .map(Authentication::getAuthorities)
                                .orElse(List.of())
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.joining(", "))
                );
            }
        }));
        setAuthenticationFailureHandler(((request, response, exception) -> log.info("[Viewith-Error] " + exception.getMessage(), exception)));
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