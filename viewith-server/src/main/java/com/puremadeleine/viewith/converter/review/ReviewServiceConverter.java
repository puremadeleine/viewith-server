package com.puremadeleine.viewith.converter.review;

import com.puremadeleine.viewith.domain.review.Review;
import com.puremadeleine.viewith.dto.review.ReviewInfoResDto;

public class ReviewServiceConverter {

    public static ReviewInfoResDto toReviewInfoResDto(Review review) {
        return ReviewInfoResDto.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .rating(review.getRating())
                .createTime(review.getCreateTime())
                .build();
    }
}
