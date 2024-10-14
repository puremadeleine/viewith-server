package com.puremadeleine.viewith.service;

import com.puremadeleine.viewith.domain.review.ReviewEntity;
import com.puremadeleine.viewith.domain.venue.SeatEntity;
import com.puremadeleine.viewith.domain.venue.VenueEntity;
import com.puremadeleine.viewith.dto.review.CreateReviewReqDto;
import com.puremadeleine.viewith.dto.review.CreateReviewResDto;
import com.puremadeleine.viewith.dto.review.ReviewInfoResDto;
import com.puremadeleine.viewith.dto.review.UpdateReviewReqDto;
import com.puremadeleine.viewith.provider.ReviewProvider;
import com.puremadeleine.viewith.provider.SeatProvider;
import com.puremadeleine.viewith.provider.VenueProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.puremadeleine.viewith.domain.review.Block.LEFT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    ReviewProvider reviewProvider = mock(ReviewProvider.class);
    VenueProvider venueProvider = mock(VenueProvider.class);
    SeatProvider seatProvider = mock(SeatProvider.class);

    ReviewService reviewService = new ReviewService(reviewProvider, venueProvider, seatProvider);

    @DisplayName("create review successfully")
    @Test
    void create_review_successfully_test() {
        // given
        var req = CreateReviewReqDto.builder()
                .venueId(1L)
                .section("A")
                .seatRow(1)
                .seatColumn(1)
                .block(LEFT)
                .rating(5.0F)
                .content("후기")
                .build();

        VenueEntity venue = getVenue();

        SeatEntity seat = getSeat();

        ReviewEntity review = getReview();

        when(venueProvider.getVenue(anyLong())).thenReturn(venue);
        when(seatProvider.getSeat(anyString(), anyInt(), anyInt())).thenReturn(seat);
        when(reviewProvider.saveReview(any(ReviewEntity.class))).thenReturn(review);

        // when
        CreateReviewResDto result = reviewService.createReview(req);

        // then
        assertThat(result.getReviewId()).isEqualTo(1L);
    }

    @DisplayName("update review successfully")
    @Test
    void update_review_successfully_test() {
        // given
        var req = UpdateReviewReqDto.builder()
                .rating(5.0F)
                .content("후기")
                .build();

        ReviewEntity review = getReview();
        when(reviewProvider.getNormalReview(anyLong())).thenReturn(review);

        // when
        reviewService.updateReview(1L, req);

        // then
        verify(reviewProvider, times(1)).getNormalReview(1L);
    }

    @DisplayName("delete review successfully")
    @Test
    void delete_review_successfully_test() {
        // given
        ReviewEntity review = getReview();
        when(reviewProvider.getNormalReview(anyLong())).thenReturn(review);

        // when
        reviewService.deleteReview(1L);

        // then
        verify(reviewProvider, times(1)).getNormalReview(1L);
    }

    @DisplayName("get review info successfully")
    @Test
    void get_review_info_successfully_test() {
        // given
        ReviewEntity review = getReview();
        when(reviewProvider.getNormalReview(anyLong())).thenReturn(review);

        // when
        ReviewInfoResDto result = reviewService.getReviewInfo(1L);

        // then
        assertThat(result.getReviewId()).isEqualTo(1L);
    }

    VenueEntity getVenue() {
        return VenueEntity.builder()
                .id(1L)
                .name("venue1")
                .build();
    }

    SeatEntity getSeat() {
        return SeatEntity.builder()
                .id(1L)
                .section("A")
                .seatRow(1)
                .seatColumn(1)
                .build();
    }

    ReviewEntity getReview() {
        return ReviewEntity.builder()
                .id(1L)
                .rating(1.5F)
                .content("후기")
                .build();
    }

}
