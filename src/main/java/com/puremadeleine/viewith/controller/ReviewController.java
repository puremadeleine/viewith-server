package com.puremadeleine.viewith.controller;

import com.puremadeleine.viewith.dto.common.SortType;
import com.puremadeleine.viewith.dto.member.MemberInfo;
import com.puremadeleine.viewith.dto.review.*;
import com.puremadeleine.viewith.service.ReviewService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/reviews")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewController {

    ReviewService reviewService;

    @PostMapping("")
    public CreateReviewResDto createReview(@RequestBody CreateReviewReqDto createReviewReqDto, MemberInfo memberInfo) {
        return reviewService.createReview(createReviewReqDto, memberInfo.getMemberId());
    }

    @GetMapping("/{review_id}")
    public ReviewInfoResDto getReview(@PathVariable("review_id") Long reviewId) {
        return reviewService.getReviewInfo(reviewId);
    }

    @PutMapping("/{review_id}")
    public void updateReview(@PathVariable("review_id") Long reviewId,
                                          @RequestBody UpdateReviewReqDto reqDto, MemberInfo memberInfo) {
        reviewService.updateReview(reviewId, reqDto, memberInfo.getMemberId());
    }

    @DeleteMapping("/{review_id}")
    public void deleteReview(@PathVariable("review_id") Long reviewId, MemberInfo memberInfo) {
        reviewService.deleteReview(reviewId, memberInfo.getMemberId());
    }

    @GetMapping("/list")
    public ReviewListResDto getReviewList(
            @RequestParam(value = "page", required = false, defaultValue = "1") @Min(1) Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") @Min(1) @Max(20) Integer size,
            @RequestParam(value = "sort_type", required = false, defaultValue = "LATEST") SortType sortType,
            @RequestParam(value = "floor") String floor,
            @RequestParam(value = "section", required = false) String section,
            @RequestParam(value = "seat_row", required = false) Integer seatRow,
            @RequestParam(value = "is_summary", required = false, defaultValue = "false") Boolean isSummary) {
        ReviewListReqDto req = ReviewListReqDto.builder()
                .page(page)
                .size(size)
                .sortType(sortType)
                .floor(floor)
                .section(section)
                .seatRow(seatRow)
                .build();
        return reviewService.getReviewList(req, isSummary);
    }

    @PostMapping("/{review_id}/report")
    public void reportReview(@PathVariable("review_id") Long reviewId,
                             @RequestBody ReportReviewReqDto req) {
        reviewService.reportReview(reviewId, req);
    }
}
