package com.puremadeleine.viewith.dto.member;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.convert.ReadingConverter;

import java.util.Arrays;

public enum OAuthType {
    KAKAO, APPLE,

    ;

    @Nullable
    public static OAuthType from(String source){
        return Arrays.stream(OAuthType.values())
                .filter(it -> StringUtils.equalsIgnoreCase(it.name(), source))
                .findAny()
                .orElse(null);
    }

    @ReadingConverter
    public static class OAuthTypeConverter implements Converter<String, OAuthType> {
        public static final OAuthTypeConverter INSANCE = new OAuthTypeConverter();

        @Nullable
        @Override
        public OAuthType convert(String value) {
            return OAuthType.from(value);
        }

        @Override
        public JavaType getInputType(TypeFactory typeFactory) {
            return typeFactory.constructType(String.class);
        }

        @Override
        public JavaType getOutputType(TypeFactory typeFactory) {
            return typeFactory.constructType(OAuthType.class);
        }
    }
}
