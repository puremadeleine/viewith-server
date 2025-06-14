package com.puremadeleine.viewith.dto.review.response;

import lombok.Builder;

@Builder
public record CreateReviewResDto(
    Long reviewId
) {
}