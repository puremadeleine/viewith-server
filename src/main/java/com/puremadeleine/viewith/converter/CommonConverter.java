package com.puremadeleine.viewith.converter;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class CommonConverter {

    public static Long toTimeStamp(LocalDateTime dateTime) {
        return (dateTime != null) ? dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() : 0L;
    }
}
