package com.puremadeleine.viewith.dto.member;

import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Value
@Jacksonized
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MemberInfo {
    OAuthType authType;
    Long memberId;
    String accessToken;
    String refreshToken;
    @Nullable
    String nickname;

    public MemberInfo updateNickname(String nickname) {
        return MemberInfo.builder()
                .authType(authType)
                .memberId(memberId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .nickname(nickname)
                .build();
    }
}
