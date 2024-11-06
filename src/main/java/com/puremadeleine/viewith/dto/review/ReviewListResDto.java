package com.puremadeleine.viewith.dto.review;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewListResDto {

    Integer page;
    Integer size;
    Integer listSize;
    Long total;
    Boolean hasNext;
    List<ReviewInfoSummaryResDto> list;

}
