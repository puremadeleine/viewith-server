package com.puremadeleine.viewith.service;

import com.puremadeleine.viewith.domain.review.ReviewEntity;
import com.puremadeleine.viewith.domain.venue.SeatEntity;
import com.puremadeleine.viewith.domain.venue.VenueEntity;
import com.puremadeleine.viewith.dto.review.*;
import com.puremadeleine.viewith.provider.ReviewProvider;
import com.puremadeleine.viewith.provider.SeatProvider;
import com.puremadeleine.viewith.provider.VenueProvider;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.puremadeleine.viewith.converter.review.ReviewServiceConverter.toReviewInfoResDto;
import static com.puremadeleine.viewith.converter.review.ReviewServiceConverter.toReviewListResDto;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewService {

    final ReviewProvider reviewProvider;
    final VenueProvider venueProvider;
    final SeatProvider seatProvider;

    @Transactional
    public CreateReviewResDto createReview(CreateReviewReqDto reqDto) {
        VenueEntity venue = venueProvider.getVenue(reqDto.getVenueId());
        SeatEntity seat = seatProvider.getSeat(reqDto.getSection(), reqDto.getSeatRow(), reqDto.getSeatColumn());
        ReviewEntity review = ReviewEntity.createReview(reqDto, venue, seat);
        ReviewEntity savedReview = reviewProvider.saveReview(review);
        return CreateReviewResDto.builder().reviewId(savedReview.getId()).build();
    }

    @Transactional
    public void updateReview(Long reviewId, UpdateReviewReqDto reqDto) {
        ReviewEntity review = reviewProvider.getNormalReview(reviewId);
        review.updateReview(reqDto);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        ReviewEntity review = reviewProvider.getNormalReview(reviewId);
        review.deleteReview();
    }

    public ReviewInfoResDto getReviewInfo(Long reviewId) {
        ReviewEntity review = reviewProvider.getNormalReview(reviewId);
        return toReviewInfoResDto(review);
    }

    public ReviewListResDto getReviewList(ReviewListReqDto req, boolean isSummary) {
        // todo : sort default로 조회할 때
        Page<ReviewEntity> reviewList = reviewProvider.getReviewList(req);
        return toReviewListResDto(isSummary, reviewList);
    }
}
