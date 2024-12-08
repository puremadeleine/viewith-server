package com.puremadeleine.viewith.dto.venue;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.convert.ReadingConverter;

import java.util.Arrays;

public enum VenueStageType {
    STAGE, THRUST_STAGE,

    ;

    @Nullable
    public static VenueStageType from(String source) {
        return Arrays.stream(VenueStageType.values())
                .filter(it -> StringUtils.equalsIgnoreCase(it.name(), source))
                .findAny()
                .orElse(null);
    }

    @ReadingConverter
    public static class VenueStageTypeConverter implements Converter<String, VenueStageType> {
        public static final VenueStageTypeConverter INSANCE = new VenueStageTypeConverter();

        @Nullable
        @Override
        public VenueStageType convert(String value) {
            return VenueStageType.from(value);
        }

        @Override
        public JavaType getInputType(TypeFactory typeFactory) {
            return typeFactory.constructType(String.class);
        }

        @Override
        public JavaType getOutputType(TypeFactory typeFactory) {
            return typeFactory.constructType(VenueStageType.class);
        }
    }
}
