package com.puremadeleine.viewith.service;

import com.puremadeleine.viewith.dto.client.AccessTokenResDto;
import com.puremadeleine.viewith.dto.client.UserInfoResDto;
import com.puremadeleine.viewith.dto.member.DuplicateNicknameResDto;
import com.puremadeleine.viewith.dto.member.JoinResDto;
import com.puremadeleine.viewith.dto.member.OAuthType;
import com.puremadeleine.viewith.dto.member.ProfileResDto;
import com.puremadeleine.viewith.exception.ViewithErrorCode;
import com.puremadeleine.viewith.exception.ViewithException;
import com.puremadeleine.viewith.provider.MemberProvider;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MemberService {
    MemberProvider memberProvider;
    KakaoService kakaoService;

    public JoinResDto login(OAuthType authType, String code){
        return switch (authType) {
            case KAKAO -> loginByKakao(code);
            case APPLE -> loginByApple(code);
            default -> throw new ViewithException(ViewithErrorCode.INVALID_PARAM);
        };
    }

    public JoinResDto loginByKakao(String code) {
        AccessTokenResDto tokenInfo = kakaoService.getAccessToken(code);

        String oauthAccessToken = tokenInfo.getAccessToken();
        String oauthRefreshToken = tokenInfo.getRefreshToken();
        UserInfoResDto kakaoUserInfo = kakaoService.getKakaoUserInfo(oauthAccessToken);



        return JoinResDto.builder().build();
    }

    public JoinResDto loginByApple(String code) {
        return JoinResDto.builder().build();
    }

    public ProfileResDto getProfile(Long memberId) {
        return ProfileResDto.builder().build();
    }

    public void putNickname(Long memberId, String nickname) {

    }

    public DuplicateNicknameResDto duplicateNickname(String nickname) {
        return DuplicateNicknameResDto.builder().build();
    }

    String makeRandomNickname() {

        return "";
    }
}
