package com.puremadeleine.viewith.dto.performance.request;

import jakarta.validation.constraints.NotBlank;

public record UpcomingPerformanceReqDto(
    @NotBlank String startDate,
    @NotBlank String endDate
) {
}