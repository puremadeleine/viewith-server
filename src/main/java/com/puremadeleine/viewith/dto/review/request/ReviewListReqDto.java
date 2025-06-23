package com.puremadeleine.viewith.dto.review.request;

import com.puremadeleine.viewith.dto.common.SortType;
import lombok.Builder;

@Builder
public record ReviewListReqDto(
    Integer page,
    Integer size,
    SortType sortType,
    String floor,
    String section,
    String seatRow
) {
}