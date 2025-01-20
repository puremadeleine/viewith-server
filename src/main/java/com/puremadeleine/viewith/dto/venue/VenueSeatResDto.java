package com.puremadeleine.viewith.dto.venue;

import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VenueSeatResDto {
    List<SeatInfoDto> seatInfos;

    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class SeatInfoDto {
        String floor;
        @Nullable
        List<Integer> rows;
    }
}
