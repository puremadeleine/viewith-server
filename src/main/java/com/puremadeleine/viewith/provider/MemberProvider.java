package com.puremadeleine.viewith.provider;

import com.puremadeleine.viewith.domain.member.MemberEntity;
import com.puremadeleine.viewith.dto.member.OAuthType;
import com.puremadeleine.viewith.exception.ViewithErrorCode;
import com.puremadeleine.viewith.exception.ViewithException;
import com.puremadeleine.viewith.repository.MemberRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MemberProvider {
    MemberRepository memberRepository;

    public boolean isNicknameUnique(String nickname) {
        return memberRepository.findByNickname(nickname).isEmpty();
    }

    public MemberEntity saveMember(MemberEntity member) {
        return memberRepository.save(member);
    }

    public Optional<MemberEntity> findActiveMember(Long memberId) {
        return memberRepository.findByIdAndDeleteYn(memberId, false);
    }

    public MemberEntity getActiveMember(Long memberId) {
        return memberRepository.findByIdAndDeleteYn(memberId, false)
                .orElseThrow(() -> new ViewithException(ViewithErrorCode.INVALID_PARAM));
    }

    public MemberEntity save(MemberEntity member) {
        return memberRepository.save(member);
    }

    public Optional<MemberEntity> findMemberByKakaoId(Long kakaoId) {
        return memberRepository.findByOauthTypeAndOauthUserId(OAuthType.KAKAO, kakaoId);
    }
}
