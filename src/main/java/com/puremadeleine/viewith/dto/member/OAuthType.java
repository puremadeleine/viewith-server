package com.puremadeleine.viewith.dto.member;

import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum OAuthType {
    KAKAO, APPLE;

    @Nullable
    public static OAuthType from(String source) {
        return Arrays.stream(OAuthType.values())
                .filter(it -> StringUtils.equalsIgnoreCase(it.name(), source))
                .findAny()
                .orElse(null);
    }

    @ReadingConverter
    public enum OAuthTypeConverter implements Converter<String, OAuthType> {
        INSTANCE;

        @Nullable
        @Override
        public OAuthType convert(String value) {
            return OAuthType.from(value);
        }
    }
}
