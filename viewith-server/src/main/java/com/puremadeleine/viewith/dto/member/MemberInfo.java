package com.puremadeleine.viewith.dto.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MemberInfo {
    OAuthType authType;
    Long oAuthMemberId;
    Long memberId;
    String oAuthAccessToken;
}
