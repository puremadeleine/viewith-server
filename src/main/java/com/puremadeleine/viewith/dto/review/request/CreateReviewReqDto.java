package com.puremadeleine.viewith.dto.review.request;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record CreateReviewReqDto(
    @NotNull Long venueId,
    @NotBlank String section,
    @NotNull @Min(1) Integer seatRow,
    @Min(1) Integer seatColumn,
    @NotBlank String content,
    @NotNull @DecimalMin("0.0") @DecimalMax("5.0") Float rating
) {}
