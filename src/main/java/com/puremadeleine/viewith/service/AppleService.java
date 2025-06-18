package com.puremadeleine.viewith.service;

import com.puremadeleine.viewith.config.client.AppleOAuthProperties;
import com.puremadeleine.viewith.dto.client.AppleJwtKeyDto;
import com.puremadeleine.viewith.dto.client.UpdateTokenResDto;
import com.puremadeleine.viewith.exception.ViewithErrorCode;
import com.puremadeleine.viewith.exception.ViewithException;
import com.puremadeleine.viewith.repository.client.AppleAuthApiRepository;
import com.puremadeleine.viewith.util.JwtUtil;
import feign.FeignException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AppleService {

    static String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
    static String GRANT_TYPE_AUTH_KEY = "authorization_code";

    AppleAuthApiRepository appleAuthApiRepository;
    AppleOAuthProperties appleProperties;
    PrivateKey appleOAuthPrivateKey;

    public UpdateTokenResDto validateAuthCode(String authCode) {
        try {
            return appleAuthApiRepository.generateAndValidationToken(GRANT_TYPE_AUTH_KEY, appleProperties.getClientId(), generateClientSecret(), authCode);
        } catch (FeignException | IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new ViewithException(ViewithErrorCode.INVALID_OAUTH_TOKEN);
        }
    }


    public UpdateTokenResDto updateAccessToken(String refreshToken) {
        try {
            return appleAuthApiRepository.generateAndValidationToken(GRANT_TYPE_REFRESH_TOKEN, appleProperties.getClientId(), generateClientSecret(), refreshToken);
        } catch (FeignException | IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new ViewithException(ViewithErrorCode.INVALID_OAUTH_TOKEN);
        }
    }

    public void revoke(String refreshToken) {
        try {
            appleAuthApiRepository.revoke(appleProperties.getClientId(), generateClientSecret(), refreshToken, GRANT_TYPE_REFRESH_TOKEN);
        } catch (FeignException | IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new ViewithException(ViewithErrorCode.INVALID_OAUTH_TOKEN);
        }
    }

    public String validateAppleOAuthAndGetSub(String idToken) {
        try {
            Claims claims = JwtUtil.getClaimsBy(getAppleJwtKey(), idToken);
            return claims.getSubject(); // Apple OAuth ID (sub)
        } catch (Exception e) {
            throw new ViewithException(ViewithErrorCode.INVALID_OAUTH_TOKEN);
        }
    }

    private AppleJwtKeyDto getAppleJwtKey() {
        return appleAuthApiRepository.getKey();
    }

    private String generateClientSecret() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        LocalDateTime expiration = LocalDateTime.now().plusMinutes(5);

        return Jwts.builder()
                .setHeaderParam(JwsHeader.KEY_ID, appleProperties.getLoginKey())
                .setIssuer(appleProperties.getTeamId())
                .setAudience(appleProperties.getAudience())
                .setSubject(appleProperties.getClientId())
                .setExpiration(Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant()))
                .setIssuedAt(new Date())
                .signWith(appleOAuthPrivateKey, SignatureAlgorithm.ES256)
                .compact();
    }


}

