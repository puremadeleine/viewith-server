package com.puremadeleine.viewith.controller;

import com.puremadeleine.viewith.common.ApiResponse;
import com.puremadeleine.viewith.dto.review.CreateReviewReqDto;
import com.puremadeleine.viewith.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("")
    public ApiResponse<Long> createReview(@RequestBody CreateReviewReqDto createReviewReqDto) {
        Long reviewId = reviewService.createReview(createReviewReqDto);
        return ApiResponse.of(reviewId);
    }

}
