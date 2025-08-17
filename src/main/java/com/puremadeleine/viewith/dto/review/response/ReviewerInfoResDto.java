package com.puremadeleine.viewith.dto.review.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewerInfoResDto {

    Long userId;
    String userNickname;
    String profileImageUrl;
}
