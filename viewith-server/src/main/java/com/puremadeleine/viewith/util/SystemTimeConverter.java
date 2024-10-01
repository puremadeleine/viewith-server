package com.puremadeleine.viewith.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

public class SystemTimeConverter {

    public static Long toTimeStamp(LocalDateTime localDateTime) {
        if (Objects.isNull(localDateTime)) return 0L;
        Instant instant = localDateTime.atZone(ZoneId.of("UTC")).toInstant();

        return instant.getEpochSecond();
    }
}
