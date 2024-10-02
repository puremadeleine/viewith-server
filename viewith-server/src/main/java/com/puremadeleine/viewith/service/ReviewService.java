package com.puremadeleine.viewith.service;

import com.puremadeleine.viewith.domain.review.Review;
import com.puremadeleine.viewith.domain.venue.Seat;
import com.puremadeleine.viewith.domain.venue.Venue;
import com.puremadeleine.viewith.dto.review.CreateReviewReqDto;
import com.puremadeleine.viewith.dto.review.CreateReviewResDto;
import com.puremadeleine.viewith.dto.review.ReviewInfoResDto;
import com.puremadeleine.viewith.dto.review.UpdateReviewReqDto;
import com.puremadeleine.viewith.repository.ReviewRepository;
import com.puremadeleine.viewith.repository.SeatRepository;
import com.puremadeleine.viewith.repository.VenueRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.puremadeleine.viewith.converter.review.ReviewServiceConverter.toReviewInfoResDto;

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
        Review review = getReview(reviewId);
        Review updateReview = Review.updateReview(review, reqDto);
        reviewRepository.save(updateReview);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        Review review = getReview(reviewId);
        Review deletedReview = Review.deleteReview(review);
        reviewRepository.save(deletedReview);
    }

    public ReviewInfoResDto getReviewInfo(Long reviewId) {
        Review review = getReview(reviewId);
        return toReviewInfoResDto(review);
    }

    private Venue getVenue(Long venueId) {
        return venueRepository.findById(venueId)
                .orElseThrow(() -> new EntityNotFoundException("Venue not found with ID: " + venueId));
    }

    private Seat getSeat(String section, Integer seatRow, Integer seatColumn) {
        return seatRepository.findBySectionAndSeatRowAndSeatColumn(section, seatRow, seatColumn)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Seat not found with section: %s, row: %s, column: %s",
                                section, seatRow, seatColumn)
                ));

    }

    private Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Seat not found with ID: " + reviewId));
    }
}
