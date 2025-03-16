package com.puremadeleine.viewith.config.web;

import com.puremadeleine.viewith.dto.common.SortType;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;

public class SortTypeConverter implements Converter<String, SortType> {
    @Override
    public SortType convert(@NonNull String source) {
        if (source.isEmpty()) {
            return SortType.LATEST;
        }
        return SortType.valueOf(source.toUpperCase()); // 대소문자 무시 변환
    }
}
