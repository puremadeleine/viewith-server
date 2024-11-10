package com.puremadeleine.viewith.service;

import com.puremadeleine.viewith.domain.review.ReviewEntity;
import com.puremadeleine.viewith.domain.review.ReviewReportEntity;
import com.puremadeleine.viewith.domain.venue.SeatEntity;
import com.puremadeleine.viewith.domain.venue.VenueEntity;
import com.puremadeleine.viewith.dto.common.SortType;
import com.puremadeleine.viewith.dto.review.*;
import com.puremadeleine.viewith.provider.ReviewProvider;
import com.puremadeleine.viewith.provider.ReviewReportProvider;
import com.puremadeleine.viewith.provider.SeatProvider;
import com.puremadeleine.viewith.provider.VenueProvider;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewService {

    ReviewProvider reviewProvider;
    VenueProvider venueProvider;
    SeatProvider seatProvider;
    ReviewReportProvider reviewReportProvider;
    ReviewServiceMapper mapper;

    @Transactional
    public CreateReviewResDto createReview(CreateReviewReqDto reqDto) {
        VenueEntity venue = venueProvider.getVenue(reqDto.getVenueId());
        SeatEntity seat = seatProvider.getSeat(reqDto.getSection(), reqDto.getSeatRow(), reqDto.getSeatColumn());
        ReviewEntity review = ReviewEntity.createReview(reqDto, venue, seat);
        ReviewEntity savedReview = reviewProvider.saveReview(review);
        return CreateReviewResDto.builder().reviewId(savedReview.getId()).build();
    }

    @Transactional
    public void updateReview(Long reviewId, UpdateReviewReqDto reqDto) {
        ReviewEntity review = reviewProvider.getNormalReview(reviewId);
        review.updateReview(reqDto);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        ReviewEntity review = reviewProvider.getNormalReview(reviewId);
        review.deleteReview();
    }

    public ReviewInfoResDto getReviewInfo(Long reviewId) {
        ReviewEntity review = reviewProvider.getNormalReview(reviewId);
        return mapper.toReviewInfoResDto(review);
    }

    public ReviewListResDto getReviewList(ReviewListReqDto req, boolean isSummary) {
        Page<ReviewEntity> reviewList = (SortType.DEFAULT.equals(req.getSortType()))
                ? reviewProvider.getReviewListPrioritizingMedia(req)
                : reviewProvider.getReviewList(req);
        return isSummary ? mapper.toReviewListResDtoWhenSummary(reviewList) : mapper.toReviewListResDto(reviewList);
    }

    public void reportReview(Long reviewId, ReportReviewReqDto req) {
        ReviewEntity review = reviewProvider.getNormalReview(reviewId);
        review.reportReview();
        ReviewReportEntity reviewReport =
                ReviewReportEntity.createReviewReport(review, req.getReportReason(), req.getReportReasonDetail());
        reviewReportProvider.saveReviewReport(reviewReport);
    }

    @Mapper(componentModel = "spring")
    public interface ReviewServiceMapper {

        @Mapping(source = "id", target = "reviewId")
        ReviewInfoResDto toReviewInfoResDto(ReviewEntity review);

        @Mapping(expression = "java(reviewList.getPageable().getPageNumber() + 1)", target = "page")
        @Mapping(source = "reviewList.size", target = "size")
        @Mapping(expression = "java(reviewList.getContent().size())", target = "listSize")
        @Mapping(source = "reviewList.totalElements", target = "total")
        @Mapping(expression = "java(reviewList.hasNext())", target = "hasNext")
        @Mapping(
                expression = "java(" +
                        "reviewList.getContent().stream()" +
                        ".map(this::toReviewInfoSummaryResDto)" +
                        ".collect(java.util.stream.Collectors.toList()))",
                target = "list")
        ReviewListResDto toReviewListResDto(Page<ReviewEntity> reviewList);

        @Mapping(source = "id", target = "reviewId")
        @Mapping(target = "summary", ignore = true)
        ReviewInfoSummaryResDto toReviewInfoSummaryResDto(ReviewEntity review);

        @Mapping(expression = "java(reviewList.getPageable().getPageNumber() + 1)", target = "page")
        @Mapping(source = "reviewList.size", target = "size")
        @Mapping(expression = "java(reviewList.getContent().size())", target = "listSize")
        @Mapping(source = "reviewList.totalElements", target = "total")
        @Mapping(expression = "java(reviewList.hasNext())", target = "hasNext")
        @Mapping(
                expression = "java(" +
                        "reviewList.getContent().stream()" +
                        ".map(this::toReviewInfoSummaryResDtoWhenSummary)" +
                        ".collect(java.util.stream.Collectors.toList()))",
                target = "list")
        ReviewListResDto toReviewListResDtoWhenSummary(Page<ReviewEntity> reviewList);

        @Mapping(expression =
                "java(review.getContent().substring(0, Math.min(review.getContent().length(), 30)))",
                target = "summary")
        @Mapping(source = "id", target = "reviewId")
        @Mapping(target = "content", ignore = true)
        ReviewInfoSummaryResDto toReviewInfoSummaryResDtoWhenSummary(ReviewEntity review);
    }

}