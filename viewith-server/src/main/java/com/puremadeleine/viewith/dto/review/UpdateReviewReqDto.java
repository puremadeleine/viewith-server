package com.puremadeleine.viewith.dto.review;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateReviewReqDto {

    String content;
    Float rating;
}