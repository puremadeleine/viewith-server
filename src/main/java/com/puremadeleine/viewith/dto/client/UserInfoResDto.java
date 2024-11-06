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
public class UserInfoResDto {
    Long id;
    @Nullable
    KakaoAccount kakaoAccount;

    @Getter
    @Jacksonized
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public static class KakaoAccount {
        @Nullable
        String email;
    }
}
