package com.puremadeleine.viewith.dto.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.util.Arrays;

public enum SortType {

    DEFAULT,        // 기본값
    LATEST,         // 최신순
    RATING;          // 별점순

    public static SortType from(String source) {
        return Arrays.stream(SortType.values())
                .filter(it -> StringUtils.equalsIgnoreCase(it.name(), source))
                .findAny()
                .orElse(LATEST);
    }

    @ReadingConverter
    public enum SortTypeConverter implements Converter<String, SortType> {
        INSTANCE;

        @Override
        public SortType convert(String source) {
            return SortType.from(source);
        }
    }
}
