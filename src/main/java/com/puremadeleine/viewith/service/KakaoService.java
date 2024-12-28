package com.puremadeleine.viewith.service;

import com.puremadeleine.viewith.config.client.KakaoOAuthProperties;
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
    static String TOKEN_PREFIX = "Bearer ";

    KakaoOAuthProperties kakaoOAuthProperties;
    KakaoOAuthRepository kakaoOAuthRepository;
    KakaoApiRepository kakaoApiRepository;

    public UserInfoResDto getAccessTokenInfo(String accessToken) {
        return kakaoApiRepository.getUserInfo(TOKEN_PREFIX + accessToken);
    }

    public void unlink(String accessToken) {
        // 동의 해제
        kakaoApiRepository.unlink(TOKEN_PREFIX + accessToken);
    }

    public void logout(String accessToken) {

    }

    public void updateAccessToken(String accessToken) {

    }
}

