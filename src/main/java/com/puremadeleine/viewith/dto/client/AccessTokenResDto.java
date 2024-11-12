package com.puremadeleine.viewith.dto.client;

import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccessTokenResDto {
    String tokenType;
    String accessToken;
    @Nullable
    String idToken;
    int expiresIn;
    String refreshToken;
    Integer refreshTokenExpiresIn;
    @Nullable
    String scope;
}
