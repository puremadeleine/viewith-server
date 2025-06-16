package com.puremadeleine.viewith.service;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.puremadeleine.viewith.config.client.AppleOAuthProperties;
import com.puremadeleine.viewith.dto.client.UpdateTokenResDto;
import com.puremadeleine.viewith.exception.ViewithErrorCode;
import com.puremadeleine.viewith.exception.ViewithException;
import com.puremadeleine.viewith.repository.client.AppleAuthApiRepository;
import feign.FeignException;
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
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AppleService {

    static String GRANT_TYPE_VALUE = "refresh_token";

    AppleAuthApiRepository appleAuthApiRepository;
    AppleOAuthProperties appleProperties;
    PrivateKey appleOAuthPrivateKey;

    public UpdateTokenResDto updateAccessToken(String refreshToken) {
        try {
            return appleAuthApiRepository.refresh(GRANT_TYPE_VALUE, appleProperties.getClientId(), generateClientSecret(), refreshToken);
        } catch (FeignException | IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new ViewithException(ViewithErrorCode.INVALID_OAUTH_TOKEN);
        }
    }

    public String getAppleOAuthId(String idToken) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(idToken);
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            return claims.getSubject();
        } catch (ParseException e) {
            throw new ViewithException(ViewithErrorCode.INVALID_OAUTH_TOKEN);
        }
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

