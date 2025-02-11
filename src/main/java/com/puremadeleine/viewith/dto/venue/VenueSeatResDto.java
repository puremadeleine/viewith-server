package com.puremadeleine.viewith.dto.venue;

import com.puremadeleine.viewith.domain.review.Block;
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
        String section;
        List<RowInfoDto> rows;
    }

    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class RowInfoDto {
        Integer row;
        List<ColumnInfoDto> columns;
    }

    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class ColumnInfoDto {
        Integer column;
        Block block;
    }
}
