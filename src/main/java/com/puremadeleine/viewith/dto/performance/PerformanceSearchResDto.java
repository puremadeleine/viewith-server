package com.puremadeleine.viewith.dto.performance;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PerformanceSearchResDto {

    String performanceTitle;
    String venueName;
    LocalDateTime performanceStartDate;
    LocalDateTime performanceEndDate;

}
