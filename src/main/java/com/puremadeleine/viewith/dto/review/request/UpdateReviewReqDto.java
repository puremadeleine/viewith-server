package com.puremadeleine.viewith.dto.review.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UpdateReviewReqDto(

    @NotBlank String content,
    @NotNull @DecimalMin("0.0") @DecimalMax("5.0") Float rating
) {}
