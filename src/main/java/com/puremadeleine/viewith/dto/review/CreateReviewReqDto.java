package com.puremadeleine.viewith.dto.review;

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
    String content;
    Float rating;
}
