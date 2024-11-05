package com.puremadeleine.viewith.service;

import com.puremadeleine.viewith.aware.SpringProxyAware;
import com.puremadeleine.viewith.domain.member.MemberEntity;
import com.puremadeleine.viewith.dto.client.AccessTokenResDto;
import com.puremadeleine.viewith.dto.client.UserInfoResDto;
import com.puremadeleine.viewith.dto.member.*;
import com.puremadeleine.viewith.exception.ViewithErrorCode;
import com.puremadeleine.viewith.exception.ViewithException;
import com.puremadeleine.viewith.provider.MemberProvider;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

import static com.puremadeleine.viewith.domain.member.MemberEntity.createKakaoMember;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MemberService extends SpringProxyAware<MemberService> {

    static String[] NICKNAME_PREFIX = {"잘보이는", "잘난"};
    static String[] NICKNAME_POSTFIX = {"꿀벌", "도롱뇽", "물개"};

    MemberProvider memberProvider;
    KakaoService kakaoService;
    JwtService jwtService;

    public JoinResDto login(OAuthType authType, String code) {
        return switch (authType) {
            case KAKAO -> getProxy().loginByKakao(code);
            case APPLE -> loginByApple(code);
            default -> throw new ViewithException(ViewithErrorCode.INVALID_PARAM);
        };
    }

    @Transactional
    public JoinResDto loginByKakao(String code) {
        // Kakao 인증 및 유저 정보 조회
        AccessTokenResDto tokenInfo = kakaoService.getAccessToken(code);
        String oauthAccessToken = tokenInfo.getAccessToken();
        UserInfoResDto kakaoUserInfo = kakaoService.getKakaoUserInfo(oauthAccessToken);

        // DB 정보 조회 및 handle
        Optional<MemberEntity> optionalMember = memberProvider.findMemberByKakaoId(kakaoUserInfo.getId());
        MemberEntity member = optionalMember.map(this::handleExistingMember)
                .orElseGet(() -> createAndSaveNewMember(kakaoUserInfo));

        // token 생성
        MemberInfo memberInfo = MemberInfo.builder()
                .authType(OAuthType.KAKAO)
                .memberId(member.getId())
                .accessToken(oauthAccessToken)
                .refreshToken(tokenInfo.getRefreshToken())
                .build();
        String accessToken = jwtService.makeAccessToken(memberInfo, tokenInfo.getExpiresIn());
        String refreshToken = jwtService.makeRefreshToken(memberInfo, tokenInfo.getRefreshTokenExpiresIn());

        return JoinResDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .nickname(member.getNickname())
                .build();
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

    private MemberEntity handleExistingMember(MemberEntity existingMember) {
        if (existingMember.getDeleteYn()) {
            existingMember.updateDeleteYn(false);
            return memberProvider.save(existingMember);
        }
        return existingMember;
    }

    private MemberEntity createAndSaveNewMember(UserInfoResDto kakaoUserInfo) {
        MemberEntity newMember = createKakaoMember(kakaoUserInfo, makeRandomNickname());
        return memberProvider.save(newMember);
    }

    private static String makeRandomNickname() {
        return NICKNAME_PREFIX[getRandomNumber(NICKNAME_PREFIX.length)] + NICKNAME_POSTFIX[getRandomNumber(NICKNAME_POSTFIX.length)];
    }

    private static int getRandomNumber(int size) {
        return new Random().nextInt(size);
    }
}
