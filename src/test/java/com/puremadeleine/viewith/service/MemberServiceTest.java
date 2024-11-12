package com.puremadeleine.viewith.service;

import com.puremadeleine.viewith.dto.member.ValidateNicknameResDto;
import com.puremadeleine.viewith.exception.ViewithErrorCode;
import com.puremadeleine.viewith.exception.ViewithException;
import com.puremadeleine.viewith.provider.MemberProvider;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MemberServiceTest {

    MemberProvider memberProvider = mock(MemberProvider.class);
    KakaoService kakaoService = mock(KakaoService.class);
    JwtService jwtService = mock(JwtService.class);

    MemberService memberService = new MemberService(memberProvider, kakaoService, jwtService);

    @ParameterizedTest
    @ValueSource(strings = {"한글만", "asd", "숫자1포함", "123", "공 백 포 함", "공백    포함", "딱10글자일때테스트", "한asd123한"})
    void validateNickname(String nickname) {
        //given
        when(memberProvider.isNicknameUnique(nickname)).thenReturn(true);

        //when
        ValidateNicknameResDto actual = memberService.validateNickname(nickname);

        //then
        assertThat(actual).isNotNull();
        assertThat(actual.getIsValidated()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"10글자초과78901", " 공백시작", "공백종료 ", "   ", " ", "특수문자#$"})
    void validateNickname_fail_test(String nickname) {
        //when, then
        assertThatThrownBy(() -> memberService.validateNickname(nickname))
                .isInstanceOf(ViewithException.class)
                .hasMessageContaining(ViewithErrorCode.INVALID_NICKNAME_FORMAT.getMessage());
    }
}