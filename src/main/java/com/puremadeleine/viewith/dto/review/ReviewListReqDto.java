package com.puremadeleine.viewith.dto.review;

import com.puremadeleine.viewith.dto.common.SortType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewListReqDto {

    Integer page;
    Integer size;
    SortType sortType;
    String floor;
    String section;
    Integer seatRow;
}
