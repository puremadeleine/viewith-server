package com.puremadeleine.viewith.provider;

import com.puremadeleine.viewith.domain.review.ReviewEntity;
import com.puremadeleine.viewith.domain.review.Status;
import com.puremadeleine.viewith.dto.common.SortType;
import com.puremadeleine.viewith.dto.review.ReviewListReqDto;
import com.puremadeleine.viewith.exception.ViewithException;
import com.puremadeleine.viewith.repository.ReviewCustomRepository;
import com.puremadeleine.viewith.repository.ReviewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

import static com.puremadeleine.viewith.exception.ViewithErrorCode.NO_NORMAL_REVIEW;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReviewProviderTest {

    ReviewRepository reviewRepository = mock(ReviewRepository.class);
    ReviewCustomRepository reviewCustomRepository = mock(ReviewCustomRepository.class);

    ReviewProvider reviewProvider = new ReviewProvider(reviewRepository, reviewCustomRepository);

    @DisplayName("return the reviewEntity when the review is successfully returned")
    @Test
    void return_reviewEntity_successfully_test() {
        // given
        ReviewEntity review = ReviewEntity.builder()
                .id(1L)
                .rating(1.5F)
                .content("후기")
                .build();

        when(reviewRepository.findByIdAndStatus(anyLong(), any(Status.class))).thenReturn(Optional.ofNullable(review));

        // when
        ReviewEntity result = reviewProvider.getNormalReview(1L);

        // then
        assertThat(result.getId()).isEqualTo(1L);
    }

    @DisplayName("throw exception when the review retrieval fails")
    @Test
    void throw_exception_when_return_null() {
        // given
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        ViewithException result = assertThrows(ViewithException.class,
                () -> reviewProvider.getNormalReview(1L));

        // then
        assertThat(result.getErrorCode().getCode()).isEqualTo(NO_NORMAL_REVIEW.getCode());
    }

    @DisplayName("return reviewEntity when save review successfully")
    @Test
    void save_reviewEntity_successfully_test() {
        // given
        ReviewEntity review = ReviewEntity.builder()
                .id(1L)
                .rating(1.5F)
                .content("후기")
                .build();

        when(reviewRepository.save(any(ReviewEntity.class))).thenReturn(review);

        // when
        ReviewEntity savedReview = reviewProvider.saveReview(review);

        // then
        assertThat(savedReview.getId()).isEqualTo(1L);
    }

    @DisplayName("return the page of reviewEntity list when the review list is successfully returned")
    @Test
    void get_reviewList_successfully_test() {
        // given
        int page = 1;
        int size = 10;
        int total = 20;

        ReviewEntity review = ReviewEntity.builder()
                .id(1L)
                .rating(1.5F)
                .content("후기")
                .build();
        when(reviewCustomRepository.findReviewList(any(ReviewListReqDto.class))).thenReturn(List.of(review));
        when(reviewCustomRepository.countReviewTotal(any(ReviewListReqDto.class))).thenReturn(total);

        ReviewListReqDto req = ReviewListReqDto.builder()
                .page(page + 1)
                .size(size)
                .sortType(SortType.DEFAULT)
                .floor("1")
                .build();

        // when
        Page<ReviewEntity> result = reviewProvider.getReviewList(req);

        // then
        assertThat(result.getTotalElements()).isEqualTo(20L);
        assertThat(result.getPageable().getPageNumber()).isEqualTo(1);
        assertThat(result.getSize()).isEqualTo(10);
        assertThat(result.getContent().size()).isEqualTo(1);
        assertThat(result.hasNext()).isFalse();
    }

    @DisplayName("return the page of reviewEntity list when the review list prioritizing media is successfully returned")
    @Test
    void get_reviewListPrioritizingMedia_successfully_test() {
        // given
        int page = 1;
        int size = 10;
        int total = 20;

        ReviewEntity review = ReviewEntity.builder()
                .id(1L)
                .rating(1.5F)
                .content("후기")
                .build();
        when(reviewCustomRepository.findReviewListPrioritizingMedia(any(ReviewListReqDto.class)))
                .thenReturn(List.of(review));
        when(reviewCustomRepository.countReviewTotal(any(ReviewListReqDto.class)))
                .thenReturn(total);

        ReviewListReqDto req = ReviewListReqDto.builder()
                .page(page + 1)
                .size(size)
                .sortType(SortType.DEFAULT)
                .floor("1")
                .build();

        // when
        Page<ReviewEntity> result = reviewProvider.getReviewListPrioritizingMedia(req);

        // then
        assertThat(result.getTotalElements()).isEqualTo(20L);
        assertThat(result.getPageable().getPageNumber()).isEqualTo(1);
        assertThat(result.getSize()).isEqualTo(10);
        assertThat(result.getContent().size()).isEqualTo(1);
        assertThat(result.hasNext()).isFalse();
    }
}