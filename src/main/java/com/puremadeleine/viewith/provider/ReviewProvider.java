package com.puremadeleine.viewith.provider;

import com.puremadeleine.viewith.domain.review.ReviewEntity;
import com.puremadeleine.viewith.domain.review.Status;
import com.puremadeleine.viewith.dto.review.ReviewCntDto;
import com.puremadeleine.viewith.dto.review.ReviewListReqDto;
import com.puremadeleine.viewith.exception.ViewithException;
import com.puremadeleine.viewith.repository.ReviewCustomRepository;
import com.puremadeleine.viewith.repository.ReviewRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.puremadeleine.viewith.exception.ViewithErrorCode.NO_NORMAL_REVIEW;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewProvider {

    ReviewRepository reviewRepository;
    ReviewCustomRepository reviewCustomRepository;

    public ReviewEntity saveReview(ReviewEntity review) {
        return reviewRepository.save(review);
    }

    public ReviewEntity getNormalReview(Long reviewId) {
        return reviewRepository.findByIdAndStatus(reviewId, Status.NORMAL)
                .orElseThrow(() -> new ViewithException(NO_NORMAL_REVIEW));
    }

    public Page<ReviewEntity> getReviewList(ReviewListReqDto req) {
        List<ReviewEntity> reviewList = reviewCustomRepository.findReviewList(req);
        int total = reviewCustomRepository.countReviewTotal(req);
        return new PageImpl<>(reviewList, PageRequest.of(req.getPage() - 1, req.getSize()), total);
    }

    public Page<ReviewEntity> getReviewListPrioritizingMedia(ReviewListReqDto req) {
        List<ReviewEntity> reviewList = reviewCustomRepository.findReviewListPrioritizingMedia(req);
        int total = reviewCustomRepository.countReviewTotal(req);
        return new PageImpl<>(reviewList, PageRequest.of(req.getPage() - 1, req.getSize()), total);
    }

    public List<ReviewCntDto> countNormalReviewsByVenueAndSeat(Long venueId) {
        return countReviewsByVenueAndSeatAndStatus(venueId, Status.NORMAL);
    }

    public List<ReviewCntDto> countReviewsByVenueAndSeatAndStatus(Long venueId, Status status) {
        return reviewRepository.countReviewsBySeat(venueId, status);
    }
}
