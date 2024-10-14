package com.puremadeleine.viewith.controller;

import com.puremadeleine.viewith.dto.review.CreateReviewReqDto;
import com.puremadeleine.viewith.dto.review.CreateReviewResDto;
import com.puremadeleine.viewith.dto.review.ReviewInfoResDto;
import com.puremadeleine.viewith.dto.review.UpdateReviewReqDto;
import com.puremadeleine.viewith.service.ReviewService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/reviews")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewController {

    final ReviewService reviewService;

    @PostMapping("")
    public CreateReviewResDto createReview(@RequestBody CreateReviewReqDto createReviewReqDto) {
        CreateReviewResDto review = reviewService.createReview(createReviewReqDto);
        return review;
    }

    @GetMapping("/{review_id}")
    public ReviewInfoResDto getReview(@PathVariable("review_id") Long reviewId) {
        ReviewInfoResDto reviewInfo = reviewService.getReviewInfo(reviewId);
        return reviewInfo;
    }

    @PutMapping("/{review_id}")
    public void updateReview(@PathVariable("review_id") Long reviewId,
                                          @RequestBody UpdateReviewReqDto reqDto) {
        reviewService.updateReview(reviewId, reqDto);
    }

    @DeleteMapping("/{review_id}")
    public void deleteReview(@PathVariable("review_id") Long reviewId) {
        reviewService.deleteReview(reviewId);
    }
}
