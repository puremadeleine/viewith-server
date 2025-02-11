package com.puremadeleine.viewith.util;

import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

import static java.util.Objects.isNull;

@UtilityClass
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TimeUtil {

    @Nullable
    public Long toNullableTimestamp(@Nullable LocalDateTime dateTime) {
        return isNull(dateTime) ? null : dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public long toTimestampOrZero(@Nullable LocalDateTime dateTime) {
        return Objects.requireNonNullElse(toNullableTimestamp(dateTime), 0L);
    }
}
