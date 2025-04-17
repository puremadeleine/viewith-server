package com.puremadeleine.viewith.dto.performance;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PerformanceSearchListResDto {

    List<PerformanceSearchResDto> performances;

    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class PerformanceSearchResDto {
        String performanceTitle;
        String venueName;
        Long performanceStartDate;
        Long performanceEndDate;
    }
}
