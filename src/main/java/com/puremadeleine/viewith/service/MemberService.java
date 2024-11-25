package com.puremadeleine.viewith.service;

import com.puremadeleine.viewith.aware.SpringProxyAware;
import com.puremadeleine.viewith.constants.NicknameConstants;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

import static com.puremadeleine.viewith.domain.member.MemberEntity.createKakaoMember;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MemberService extends SpringProxyAware<MemberService> {

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

    public void putNickname(MemberInfo member, String nickname) {
        if (!StringUtils.equals(member.getNickname(), nickname)) {
            validateNickname(nickname);
            MemberEntity activeMember = memberProvider.getActiveMember(member.getMemberId());
            activeMember.updateNickname(nickname);
            memberProvider.saveMember(activeMember);
        }
    }

    public ValidateNicknameResDto validateNickname(String nickname) {
        if (!nickname.matches(NicknameConstants.PATTERN)) {
            throw new ViewithException(ViewithErrorCode.INVALID_NICKNAME_FORMAT);
        }

        if (!memberProvider.isNicknameUnique(nickname)) {
            throw new ViewithException(ViewithErrorCode.DUPLICATED_NICKNAME);
        }

        return ValidateNicknameResDto.builder().isValidated(true).build();
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

    private String makeRandomNickname() {
        String nickname = makeNickname();
        while (!memberProvider.isNicknameUnique(nickname)) {
            nickname = makeNickname();
        }
        return nickname;
    }

    private static String makeNickname() {
        // 수식어(최대 4글자) + 띄어쓰기 + 동물(최대 5글자) + 랜덤 숫자
        String nickname = NicknameConstants.PREFIXES[getRandomNumber(NicknameConstants.PREFIXES.length)]
                + NicknameConstants.MIDDLE
                + NicknameConstants.ANIMALS[getRandomNumber(NicknameConstants.ANIMALS.length)]
                + getRandomNumber(9999);
        int max = Integer.parseInt(NicknameConstants.MAX);
        nickname = nickname.length() > max ? nickname.substring(0, max) : nickname;
        return nickname;
    }

    private static int getRandomNumber(int size) {
        return new Random().nextInt(size);
    }
}
