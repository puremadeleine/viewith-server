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
public class VenueFilterResDto {
    List<FilterInfoDto> seatInfos;

    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class FilterInfoDto {
        String floor;
        @Nullable
        List<Integer> rows;
    }
}
