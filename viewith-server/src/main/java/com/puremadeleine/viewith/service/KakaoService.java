package com.puremadeleine.viewith.service;

import com.puremadeleine.viewith.config.KakaoOAuthProperties;
import com.puremadeleine.viewith.dto.client.AccessTokenResDto;
import com.puremadeleine.viewith.dto.client.UserInfoResDto;
import com.puremadeleine.viewith.repository.client.KakaoApiRepository;
import com.puremadeleine.viewith.repository.client.KakaoOAuthRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KakaoService {
    static String GRANT_TYPE = "authorization_code";
    static String KAKAO_INFO = "[\"kakao_account.email\"]";
    static String TOKEN_PREFIX = "Bearer ";

    KakaoOAuthProperties kakaoOAuthProperties;
    KakaoOAuthRepository kakaoOAuthRepository;
    KakaoApiRepository kakaoApiRepository;

    public AccessTokenResDto getAccessToken(String code) {
        return kakaoOAuthRepository.getAccessToken(GRANT_TYPE, kakaoOAuthProperties.getClientId(), kakaoOAuthProperties.getRedirectUri(), code, kakaoOAuthProperties.getClientSecret());
    }

    public UserInfoResDto getKakaoUserInfo(String accessToken) {
        return kakaoApiRepository.getUserInfo(TOKEN_PREFIX + accessToken, null, KAKAO_INFO);
    }

    public void logout(String accessToken) {

    }

    public void updateAccessToken(String accessToken) {

    }
}

