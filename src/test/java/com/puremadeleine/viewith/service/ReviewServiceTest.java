package com.puremadeleine.viewith.service;

import com.puremadeleine.viewith.domain.review.ReportReason;
import com.puremadeleine.viewith.domain.review.ReviewEntity;
import com.puremadeleine.viewith.domain.review.Status;
import com.puremadeleine.viewith.domain.venue.SeatEntity;
import com.puremadeleine.viewith.domain.venue.VenueEntity;
import com.puremadeleine.viewith.dto.common.SortType;
import com.puremadeleine.viewith.dto.review.*;
import com.puremadeleine.viewith.provider.ReviewProvider;
import com.puremadeleine.viewith.provider.ReviewReportProvider;
import com.puremadeleine.viewith.provider.SeatProvider;
import com.puremadeleine.viewith.provider.VenueProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static com.puremadeleine.viewith.domain.review.Block.LEFT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    ReviewProvider reviewProvider = mock(ReviewProvider.class);
    VenueProvider venueProvider = mock(VenueProvider.class);
    SeatProvider seatProvider = mock(SeatProvider.class);
    ReviewReportProvider reviewReportProvider = mock(ReviewReportProvider.class);
    ReviewService.ReviewServiceMapper reviewServiceMapper = Mappers.getMapper(ReviewService.ReviewServiceMapper.class);

    ReviewService reviewService = new ReviewService(
            reviewProvider, venueProvider, seatProvider,reviewReportProvider, reviewServiceMapper);

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

    @DisplayName("get review list prioritizing media when sort type is default")
    @Test
    void get_review_list_when_sort_type_default() {
        // given
        int page = 1;
        int size = 10;
        long total = 20;

        Page<ReviewEntity> reviewList = getReviewList(page, size, total);
        when(reviewProvider.getReviewListPrioritizingMedia(any(ReviewListReqDto.class)))
                .thenReturn(reviewList);

        ReviewListReqDto req = ReviewListReqDto.builder()
                .page(page)
                .size(size)
                .sortType(SortType.DEFAULT)
                .floor("1")
                .build();

        // when
        ReviewListResDto result = reviewService.getReviewList(req, false);

        // then
        assertThat(result.getList().size()).isEqualTo(1);
        assertThat(result.getPage()).isEqualTo(1);
        assertThat(result.getTotal()).isEqualTo(20);
        assertThat(result.getList().getFirst().getContent()).isEqualTo("후기");
        assertThat(result.getList().getFirst().getSummary()).isNull();
    }

    @DisplayName("get review list when sort type is not default")
    @Test
    void get_review_list_when_sort_type_is_not_default() {
        // given
        int page = 1;
        int size = 10;
        long total = 20;

        Page<ReviewEntity> reviewList = getReviewList(page, size, total);
        when(reviewProvider.getReviewList(any(ReviewListReqDto.class)))
                .thenReturn(reviewList);

        ReviewListReqDto req = ReviewListReqDto.builder()
                .page(page)
                .size(size)
                .sortType(SortType.RATING)
                .floor("1")
                .build();

        // when
        ReviewListResDto result = reviewService.getReviewList(req, false);

        // then
        assertThat(result.getList().size()).isEqualTo(1);
        assertThat(result.getPage()).isEqualTo(1);
        assertThat(result.getTotal()).isEqualTo(20);
    }

    @DisplayName("get review summary list when sort type is not default")
    @Test
    void get_review_summary_list_when_sort_type_is_not_default_is_summary_true() {
        // given
        int page = 1;
        int size = 10;
        long total = 20;

        Page<ReviewEntity> reviewList = getReviewList(page, size, total);
        when(reviewProvider.getReviewList(any(ReviewListReqDto.class)))
                .thenReturn(reviewList);

        ReviewListReqDto req = ReviewListReqDto.builder()
                .page(page)
                .size(size)
                .sortType(SortType.RATING)
                .floor("1")
                .build();

        // when
        ReviewListResDto result = reviewService.getReviewList(req, true);

        // then
        assertThat(result.getList().size()).isEqualTo(1);
        assertThat(result.getPage()).isEqualTo(1);
        assertThat(result.getTotal()).isEqualTo(20);
        assertThat(result.getList().getFirst().getSummary()).isEqualTo("후기");
        assertThat(result.getList().getFirst().getContent()).isNull();
    }

    @DisplayName("review status change to REPORTED when review report count is 4 before reported")
    @Test
    void review_status_change_to_REPORTED_when_review_report_count_is_4_before_reported_test() {
        // given
        Long reviewId = 1L;
        ReviewEntity review = ReviewEntity.builder()
                .id(reviewId)
                .rating(1.5F)
                .content("후기")
                .reportCount(4)
                .build();
        when(reviewProvider.getNormalReview(anyLong())).thenReturn(review);

        ReportReviewReqDto req = ReportReviewReqDto.builder().reportReason(ReportReason.OFFENSIVE).build();

        // when
        reviewService.reportReview(reviewId, req);

        // then
        assertThat(review.getReportCount()).isEqualTo(5);
        assertThat(review.getStatus()).isEqualTo(Status.REPORTED);
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

    Page<ReviewEntity> getReviewList(Integer page, Integer size, Long total) {
        List<ReviewEntity> reviewList = List.of(getReview());
        return new PageImpl<>(reviewList, PageRequest.of(page - 1, size), total);
    }

}
