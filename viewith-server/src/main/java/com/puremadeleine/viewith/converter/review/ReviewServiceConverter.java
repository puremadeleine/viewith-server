package com.puremadeleine.viewith.converter.review;

import com.puremadeleine.viewith.domain.review.Review;
import com.puremadeleine.viewith.dto.review.ReviewInfoResDto;

import static com.puremadeleine.viewith.util.SystemTimeConverter.toTimeStamp;

public class ReviewServiceConverter {

    public static ReviewInfoResDto toReviewInfoResDto(Review review) {
        return ReviewInfoResDto.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .rating(review.getRating())
                .createTime(toTimeStamp(review.getCreateTime()))
                .build();
    }
}