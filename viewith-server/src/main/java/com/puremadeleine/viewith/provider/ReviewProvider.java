package com.puremadeleine.viewith.provider;

import com.puremadeleine.viewith.domain.review.ReviewEntity;
import com.puremadeleine.viewith.domain.review.Status;
import com.puremadeleine.viewith.exception.ViewithException;
import com.puremadeleine.viewith.repository.ReviewRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import static com.puremadeleine.viewith.exception.ViewithErrorCode.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewProvider {

    final ReviewRepository reviewRepository;

    public ReviewEntity saveReview(ReviewEntity review) {
        return reviewRepository.save(review);
    }

    public ReviewEntity getNormalReview(Long reviewId) {
        return reviewRepository.findByIdAndStatus(reviewId, Status.NORMAL)
                .orElseThrow(() -> new ViewithException(NO_NORMAL_REVIEW));
    }
}
