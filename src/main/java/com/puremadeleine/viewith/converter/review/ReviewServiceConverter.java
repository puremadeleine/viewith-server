package com.puremadeleine.viewith.converter.review;

import com.puremadeleine.viewith.domain.member.MemberEntity;
import com.puremadeleine.viewith.domain.review.ReviewEntity;
import com.puremadeleine.viewith.domain.venue.SeatEntity;
import com.puremadeleine.viewith.dto.review.ReviewInfoSummaryResDto;
import com.puremadeleine.viewith.dto.review.ReviewListResDto;
import com.puremadeleine.viewith.util.HtmlUtils;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import static java.lang.Math.min;

public class ReviewServiceConverter {

    public static ReviewListResDto toReviewListResDto(Boolean isSummary,
                                                      Page<ReviewEntity> reviewList,
                                                      Map<Long, List<String>> reviewImageUrlMap) {
        return ReviewListResDto.builder()
                .page(reviewList.getPageable().getPageNumber() + 1)
                .size(reviewList.getSize())
                .listSize(reviewList.getContent().size())
                .total(reviewList.getTotalElements())
                .hasNext(reviewList.hasNext())
                .list(reviewList.stream()
                        .map(r -> toReviewInfoSummaryResDto(isSummary, r, reviewImageUrlMap.get(r.getId())))
                        .toList())
                .build();
    }

    public static ReviewInfoSummaryResDto toReviewInfoSummaryResDto(Boolean isSummary,
                                                                    ReviewEntity review,
                                                                    List<String> imageUrls) {

        String pureContent = HtmlUtils.removeHtml(review.getContent());
        if (Boolean.TRUE.equals(isSummary)) {
            return ReviewInfoSummaryResDto.builder()
                    .reviewId(review.getId())
                    .summary(pureContent.substring(0, min(pureContent.length(), 30)))
                    .rating(review.getRating())
                    .createTime(review.getCreateTime())
                    .imageList(imageUrls)
                    .userInfo(toReviewerInfoResDto(review.getMember()))
                    .seatInfo(toSeatInfoDto(review.getSeat()))
                    .build();
        }
        return ReviewInfoSummaryResDto.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .rating(review.getRating())
                .createTime(review.getCreateTime())
                .imageList(imageUrls)
                .userInfo(toReviewerInfoResDto(review.getMember()))
                .seatInfo(toSeatInfoDto(review.getSeat()))
                .build();
    }

    private static ReviewInfoSummaryResDto.ReviewerInfoResDto toReviewerInfoResDto(MemberEntity member) {
        return ReviewInfoSummaryResDto.ReviewerInfoResDto.builder()
                .userId(member.getId())
                .userNickname(member.getNickname())
                .build();
    }

    private static ReviewInfoSummaryResDto.SeatInfoDto toSeatInfoDto(SeatEntity seat) {
        return ReviewInfoSummaryResDto.SeatInfoDto.builder()
                .floor(seat.getFloor())
                .section(seat.getSection())
                .seatRow(seat.getSeatRow())
                .seatColumn(seat.getSeatColumn())
                .block(seat.getBlock())
                .build();
    }

}
