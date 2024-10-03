package com.puremadeleine.viewith.service;

import com.puremadeleine.viewith.domain.review.Review;
import com.puremadeleine.viewith.domain.review.Status;
import com.puremadeleine.viewith.domain.venue.Seat;
import com.puremadeleine.viewith.domain.venue.Venue;
import com.puremadeleine.viewith.dto.review.CreateReviewReqDto;
import com.puremadeleine.viewith.dto.review.CreateReviewResDto;
import com.puremadeleine.viewith.dto.review.ReviewInfoResDto;
import com.puremadeleine.viewith.dto.review.UpdateReviewReqDto;
import com.puremadeleine.viewith.exception.ViewithException;
import com.puremadeleine.viewith.repository.ReviewRepository;
import com.puremadeleine.viewith.repository.SeatRepository;
import com.puremadeleine.viewith.repository.VenueRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.puremadeleine.viewith.converter.review.ReviewServiceConverter.toReviewInfoResDto;
import static com.puremadeleine.viewith.exception.ViewithErrorCode.*;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final VenueRepository venueRepository;
    private final SeatRepository seatRepository;

    @Transactional
    public CreateReviewResDto createReview(CreateReviewReqDto reqDto) {
        Venue venue = getVenue(reqDto.getVenueId());
        Seat seat = getSeat(reqDto.getSection(), reqDto.getSeatRow(), reqDto.getSeatColumn());
        Review review = Review.createReview(reqDto, venue, seat);
        Review savedReview = reviewRepository.save(review);
        return CreateReviewResDto.builder().reviewId(savedReview.getId()).build();
    }

    @Transactional
    public void updateReview(Long reviewId, UpdateReviewReqDto reqDto) {
        Review review = getNormalReview(reviewId);
        Review updateReview = Review.updateReview(review, reqDto);
        reviewRepository.save(updateReview);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        Review review = getNormalReview(reviewId);
        Review deletedReview = Review.deleteReview(review);
        reviewRepository.save(deletedReview);
    }

    public ReviewInfoResDto getReviewInfo(Long reviewId) {
        Review review = getNormalReview(reviewId);
        return toReviewInfoResDto(review);
    }

    private Venue getVenue(Long venueId) {
        return venueRepository.findById(venueId)
                .orElseThrow(() -> new ViewithException(NO_VENUE, "The venue with ID " + venueId + " was not found."));
    }

    private Seat getSeat(String section, Integer seatRow, Integer seatColumn) {
        return seatRepository.findBySectionAndSeatRowAndSeatColumn(section, seatRow, seatColumn)
                .orElseThrow(() ->
                        new ViewithException(NO_SEAT, "The seat with section: " + section + ", seat_row: " + seatRow +
                                ", seat_column: " + seatColumn + " was not found.")
                );

    }

    private Review getNormalReview(Long reviewId) {
        return reviewRepository.findByIdAndStatus(reviewId, Status.NORMAL)
                .orElseThrow(() -> new ViewithException(NO_NORMAL_REVIEW));
    }
}
