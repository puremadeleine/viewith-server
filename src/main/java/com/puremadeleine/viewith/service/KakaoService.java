package com.puremadeleine.viewith.service;

import com.puremadeleine.viewith.config.client.KakaoOAuthProperties;
import com.puremadeleine.viewith.dto.client.KakaoUserInfoResDto;
import com.puremadeleine.viewith.dto.client.UpdateTokenResDto;
import com.puremadeleine.viewith.exception.ViewithErrorCode;
import com.puremadeleine.viewith.exception.ViewithException;
import com.puremadeleine.viewith.repository.client.KAuthApiRepository;
import com.puremadeleine.viewith.repository.client.KakaoApiRepository;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KakaoService {

    static String GRANT_TYPE_VALUE = "refresh_token";
    static String GRANT_TYPE_KEY = "grant_type";
    static String CLIENT_ID_KEY = "client_id";
    static String REFRESH_TOKEN_KEY = "refresh_token";

    static String TOKEN_PREFIX = "Bearer ";

    KakaoApiRepository kakaoApiRepository;
    KAuthApiRepository kAuthApiRepository;
    KakaoOAuthProperties kakaoOAuthProperties;

    public KakaoUserInfoResDto getAccessTokenInfo(String accessToken) {
        try {
            return kakaoApiRepository.getUserInfo(TOKEN_PREFIX + accessToken);
        } catch (FeignException e) {
            throw new ViewithException(ViewithErrorCode.INVALID_OAUTH_TOKEN);
        }
    }

    public void unlink(String accessToken) {
        // 동의 해제
        kakaoApiRepository.unlink(TOKEN_PREFIX + accessToken);
    }

    public UpdateTokenResDto updateAccessToken(String refreshToken) {
        try {
            Map<String, String> form = new HashMap<>();
            form.put(GRANT_TYPE_KEY, GRANT_TYPE_VALUE);
            form.put(CLIENT_ID_KEY, kakaoOAuthProperties.getClientId());
            form.put(REFRESH_TOKEN_KEY, refreshToken);

            return kAuthApiRepository.refresh(form);
        } catch (FeignException e) {
            throw new ViewithException(ViewithErrorCode.INVALID_OAUTH_TOKEN);
        }
    }
}

