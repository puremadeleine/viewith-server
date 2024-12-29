package com.puremadeleine.viewith.service;

import com.puremadeleine.viewith.domain.member.MemberEntity;
import com.puremadeleine.viewith.domain.review.ReportReason;
import com.puremadeleine.viewith.domain.review.ReviewEntity;
import com.puremadeleine.viewith.domain.review.Status;
import com.puremadeleine.viewith.domain.venue.SeatEntity;
import com.puremadeleine.viewith.domain.venue.VenueEntity;
import com.puremadeleine.viewith.dto.common.SortType;
import com.puremadeleine.viewith.dto.review.*;
import com.puremadeleine.viewith.exception.ViewithException;
import com.puremadeleine.viewith.provider.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static com.puremadeleine.viewith.domain.review.Block.LEFT;
import static com.puremadeleine.viewith.exception.ViewithErrorCode.PERMISSION_DENIED_FOR_REVIEW;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    ReviewProvider reviewProvider = mock(ReviewProvider.class);
    VenueProvider venueProvider = mock(VenueProvider.class);
    SeatProvider seatProvider = mock(SeatProvider.class);
    ReviewReportProvider reviewReportProvider = mock(ReviewReportProvider.class);
    MemberProvider memberProvider = mock(MemberProvider.class);
    ImageService imageService = mock(ImageService.class);
    ReviewService.ReviewServiceMapper reviewServiceMapper = Mappers.getMapper(ReviewService.ReviewServiceMapper.class);

    ReviewService reviewService = new ReviewService(
            reviewProvider, venueProvider, seatProvider,reviewReportProvider, memberProvider, imageService, reviewServiceMapper);


    @Nested
    @DisplayName("Create Review Tests")
    class CreateReviewTests {

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

            MemberEntity member = getMember(1L);

            ReviewEntity review = getReview();

            when(venueProvider.getVenue(anyLong())).thenReturn(venue);
            when(seatProvider.getSeat(anyString(), anyInt(), anyInt())).thenReturn(seat);
            when(reviewProvider.saveReview(any(ReviewEntity.class))).thenReturn(review);

            // when
            CreateReviewResDto result = reviewService.createReview(req, null, member.getId());

            // then
            assertThat(result.getReviewId()).isEqualTo(1L);
        }
    }

    @Nested
    @DisplayName("Update Review Tests")
    class UpdateReviewTests {

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
            MemberEntity member = getMember(1L);

            // when
            reviewService.updateReview(1L, req, member.getId());

            // then
            verify(reviewProvider, times(1)).getNormalReview(1L);
        }

        @DisplayName("Fail to update review when there is no permission for the review")
        @Test
        void update_review_fail_when_no_permission_for_review() {
            // given
            var req = UpdateReviewReqDto.builder()
                    .rating(5.0F)
                    .content("후기")
                    .build();

            ReviewEntity review = getReview();
            when(reviewProvider.getNormalReview(anyLong())).thenReturn(review);
            MemberEntity member = getMember(2L);

            // when
            Exception exception = assertThrows(ViewithException.class, () ->
                    reviewService.updateReview(1L, req, member.getId()));

            // then
            assertThat(exception.getMessage()).isEqualTo(PERMISSION_DENIED_FOR_REVIEW.getMessage());
        }
    }

    @Nested
    @DisplayName("Delete Review Tests")
    class DeleteReviewTests {

        @DisplayName("delete review successfully")
        @Test
        void delete_review_successfully_test() {
            // given
            ReviewEntity review = getReview();
            when(reviewProvider.getNormalReview(anyLong())).thenReturn(review);
            MemberEntity member = getMember(1L);

            // when
            reviewService.deleteReview(1L, member.getId());

            // then
            verify(reviewProvider, times(1)).getNormalReview(1L);
        }

        @DisplayName("Fail to delete review when there is no permission for the review")
        @Test
        void delete_review_fail_when_no_permission_for_review() {
            // given
            ReviewEntity review = getReview();
            when(reviewProvider.getNormalReview(anyLong())).thenReturn(review);
            MemberEntity member = getMember(2L);

            // when
            Exception exception = assertThrows(ViewithException.class, () ->
                    reviewService.deleteReview(1L, member.getId()));

            // then
            assertThat(exception.getMessage()).isEqualTo(PERMISSION_DENIED_FOR_REVIEW.getMessage());
        }
    }

    @Nested
    @DisplayName("Get Review Info Tests")
    class GetReviewInfoTests {

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
            assertThat(result.getUserInfo().getUserNickname()).isEqualTo("닉네임");
        }
    }

    @Nested
    @DisplayName("Get Review List Tests")
    class GetReviewListTests {
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
    }

    @Nested
    @DisplayName("Report Review Tests")
    class ReportReviewTests {

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
                    .member(getMember(1L))
                    .build();
            when(reviewProvider.getNormalReview(anyLong())).thenReturn(review);

            ReportReviewReqDto req = ReportReviewReqDto.builder().reportReason(ReportReason.OFFENSIVE).build();
            MemberEntity member = getMember(2L);

            // when
            reviewService.reportReview(reviewId, req, member.getId());

            // then
            assertThat(review.getReportCount()).isEqualTo(5);
            assertThat(review.getStatus()).isEqualTo(Status.REPORTED);
        }

        @DisplayName("Fail to report review when there is no permission for the review")
        @Test
        void delete_review_fail_when_no_permission_for_review() {
            // given
            Long reviewId = 1L;
            ReviewEntity review = ReviewEntity.builder()
                    .id(reviewId)
                    .rating(1.5F)
                    .content("후기")
                    .member(getMember(2L))
                    .build();
            when(reviewProvider.getNormalReview(anyLong())).thenReturn(review);

            ReportReviewReqDto req = ReportReviewReqDto.builder().reportReason(ReportReason.OFFENSIVE).build();
            MemberEntity member = getMember(2L);

            // when
            Exception exception = assertThrows(ViewithException.class, () ->
                    reviewService.reportReview(reviewId, req, member.getId()));

            // then
            assertThat(exception.getMessage()).isEqualTo(PERMISSION_DENIED_FOR_REVIEW.getMessage());
        }
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
                .member(getMember(1L))
                .build();
    }

    MemberEntity getMember(Long id) {
        return MemberEntity.builder()
                .id(id)
                .nickname("닉네임")
                .build();
    }


    Page<ReviewEntity> getReviewList(Integer page, Integer size, Long total) {
        List<ReviewEntity> reviewList = List.of(getReview());
        return new PageImpl<>(reviewList, PageRequest.of(page - 1, size), total);
    }

}
