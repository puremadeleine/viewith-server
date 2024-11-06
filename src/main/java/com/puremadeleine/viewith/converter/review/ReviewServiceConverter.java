package com.puremadeleine.viewith.converter.review;

import com.puremadeleine.viewith.domain.review.ReviewEntity;
import com.puremadeleine.viewith.dto.review.ReviewInfoResDto;
import com.puremadeleine.viewith.dto.review.ReviewInfoSummaryResDto;
import com.puremadeleine.viewith.dto.review.ReviewListResDto;
import org.springframework.data.domain.Page;

import static java.lang.Math.min;

public class ReviewServiceConverter {

    public static ReviewInfoResDto toReviewInfoResDto(ReviewEntity review) {
        return ReviewInfoResDto.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .rating(review.getRating())
                .createTime(review.getCreateTime())
                .build();
    }

    public static ReviewListResDto toReviewListResDto(Boolean isSummary, Page<ReviewEntity> reviewList) {
        return ReviewListResDto.builder()
                .page(reviewList.getPageable().getPageNumber())
                .size(reviewList.getSize())
                .listSize(reviewList.getContent().size())
                .total(reviewList.getTotalElements())
                .hasNext(reviewList.hasNext())
                .list(reviewList.stream().map(r -> toReviewInfoSummaryResDto(isSummary, r)).toList())
                .build();
    }

    public static ReviewInfoSummaryResDto toReviewInfoSummaryResDto(Boolean isSummary, ReviewEntity review) {
        if (Boolean.TRUE.equals(isSummary)) {
            return ReviewInfoSummaryResDto.builder()
                    .reviewId(review.getId())
                    .summary(review.getContent().substring(0, min(review.getContent().length(), 30)))
                    .rating(review.getRating())
                    .createTime(review.getCreateTime())
                    .build();
        }
        return ReviewInfoSummaryResDto.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .rating(review.getRating())
                .createTime(review.getCreateTime())
                .build();
    }
}
