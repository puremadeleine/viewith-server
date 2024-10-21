package com.puremadeleine.viewith.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.puremadeleine.viewith.config.auth.SecurityProperties;
import com.puremadeleine.viewith.exception.ErrorResponse;
import com.puremadeleine.viewith.exception.ViewithErrorCode;
import com.puremadeleine.viewith.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static java.util.Objects.isNull;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final SecurityProperties securityProperties;

    public JwtAuthenticationFilter(JwtService jwtService, SecurityProperties securityProperties) {
        this.jwtService = jwtService;
        this.securityProperties = securityProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        boolean isExcludeUri = securityProperties.getExcludeUris().contains(request.getRequestURI());
        String token = getJwtFromRequest(request);

        boolean skipCheck = isExcludeUri && (isNull(token) || token.isEmpty());
        if (skipCheck) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication = null;

        if (token != null && jwtService.validateAccessToken(token)) {
            authentication = jwtService.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        boolean validatedError = token == null || isNull(authentication);
        if (validatedError && !isExcludeUri) {
            respondWithError(response, HttpServletResponse.SC_UNAUTHORIZED, ViewithErrorCode.INVALID_TOKEN);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 제거
        }
        return null;
    }

    private void respondWithError(HttpServletResponse response, int status, ViewithErrorCode errorCode) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ErrorResponse errorResponse = ErrorResponse.of(errorCode);
        ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper
        String jsonResponse = objectMapper.writeValueAsString(errorResponse);

        response.getWriter().write(jsonResponse);
    }
}
