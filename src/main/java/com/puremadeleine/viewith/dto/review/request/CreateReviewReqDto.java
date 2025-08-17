package com.puremadeleine.viewith.dto.review.request;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record CreateReviewReqDto(
    @NotNull Long venueId,
    Long performanceId,
    @NotBlank String section,
    @NotNull String seatRow,
    String seatColumn,
    @NotBlank String content,
    @NotNull @DecimalMin("0.0") @DecimalMax("5.0") Float rating
) {
}