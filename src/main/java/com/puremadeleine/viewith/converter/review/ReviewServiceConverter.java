package com.puremadeleine.viewith.converter.review;

import com.puremadeleine.viewith.domain.review.ReviewEntity;
import com.puremadeleine.viewith.dto.review.ReviewInfoSummaryResDto;
import com.puremadeleine.viewith.dto.review.ReviewListResDto;
import com.puremadeleine.viewith.util.HtmlUtils;
import org.springframework.data.domain.Page;

import static java.lang.Math.min;

public class ReviewServiceConverter {

    public static ReviewListResDto toReviewListResDto(Boolean isSummary, Page<ReviewEntity> reviewList) {
        return ReviewListResDto.builder()
                .page(reviewList.getPageable().getPageNumber() + 1)
                .size(reviewList.getSize())
                .listSize(reviewList.getContent().size())
                .total(reviewList.getTotalElements())
                .hasNext(reviewList.hasNext())
                .list(reviewList.stream().map(r -> toReviewInfoSummaryResDto(isSummary, r)).toList())
                .build();
    }

    public static ReviewInfoSummaryResDto toReviewInfoSummaryResDto(Boolean isSummary, ReviewEntity review) {

        String pureContent = HtmlUtils.removeHtml(review.getContent());
        if (Boolean.TRUE.equals(isSummary)) {
            return ReviewInfoSummaryResDto.builder()
                    .reviewId(review.getId())
                    .summary(pureContent.substring(0, min(pureContent.length(), 30)))
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
