package com.puremadeleine.viewith.dto.review.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ReviewListResDto(
    Integer page,
    Integer size,
    Integer listSize,
    Long total,
    Boolean hasNext,
    List<ReviewInfoSummaryResDto> list
) {}
