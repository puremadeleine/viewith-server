package com.puremadeleine.viewith.dto.review;

import com.puremadeleine.viewith.domain.review.Block;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CreateReviewReqDto {

    Long venueId;
    String section;
    Integer seatRow;
    Integer seatColumn;
    Block block;
    String content;
    Float rating;

    // todo : 이미지 추가
}
