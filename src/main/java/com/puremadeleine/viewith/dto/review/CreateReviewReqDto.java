package com.puremadeleine.viewith.dto.review;

import com.puremadeleine.viewith.domain.review.Block;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Jacksonized
public class CreateReviewReqDto {

    Long venueId;
    String section;
    Integer seatRow;
    Integer seatColumn;
    Block block;
    String content;
    Float rating;
}
