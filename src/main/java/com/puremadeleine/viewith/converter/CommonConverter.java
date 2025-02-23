package com.puremadeleine.viewith.converter;

import jakarta.annotation.Nullable;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

import static java.util.Objects.isNull;

public class CommonConverter {

    @Nullable
    public static Long toNullableTimestamp(@Nullable LocalDateTime dateTime) {
        return isNull(dateTime) ? null : dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static Long toTimestampOrZero(LocalDateTime dateTime) {
        return Objects.requireNonNullElse(toNullableTimestamp(dateTime), 0L);
    }
}
