package com.puremadeleine.viewith.dto.review.request;

import jakarta.validation.constraints.*;
import lombok.Builder;

import static com.puremadeleine.viewith.constants.SeatConstants.UNSELECTED_STRING;

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
    public CreateReviewReqDto {
        if (seatColumn == null) {
            seatColumn = UNSELECTED_STRING;
        }
    }
}