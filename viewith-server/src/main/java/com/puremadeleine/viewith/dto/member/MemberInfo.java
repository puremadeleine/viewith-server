package com.puremadeleine.viewith.dto.member;

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
}
