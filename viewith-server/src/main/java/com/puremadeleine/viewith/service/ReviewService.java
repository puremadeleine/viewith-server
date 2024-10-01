package com.puremadeleine.viewith.service;

import com.puremadeleine.viewith.domain.review.Review;
import com.puremadeleine.viewith.domain.venue.Seat;
import com.puremadeleine.viewith.domain.venue.Venue;
import com.puremadeleine.viewith.dto.review.CreateReviewReqDto;
import com.puremadeleine.viewith.dto.review.CreateReviewResDto;
import com.puremadeleine.viewith.dto.review.ReviewInfoResDto;
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
        Seat seat = getSeat(reqDto.getSeatId());
        Review review = Review.createReview(reqDto, venue, seat);
        Review savedReview = reviewRepository.save(review);
        return CreateReviewResDto.builder().reviewId(savedReview.getId()).build();
    }

    public ReviewInfoResDto getReviewInfo(Long reviewId) {
        Review review = getReview(reviewId);
        return toReviewInfoResDto(review);
    }

    private Venue getVenue(Long venueId) {
        return venueRepository.findById(venueId)
                .orElseThrow(() -> new EntityNotFoundException("Venue not found with ID: " + venueId));
    }

    private Seat getSeat(Long seatId) {
        return seatRepository.findById(seatId)
                .orElseThrow(() -> new EntityNotFoundException("Seat not found with ID: " + seatId));
    }

    private Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Seat not found with ID: " + reviewId));
    }
}
