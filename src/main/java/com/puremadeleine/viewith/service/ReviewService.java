package com.puremadeleine.viewith.service;

import com.puremadeleine.viewith.domain.member.MemberEntity;
import com.puremadeleine.viewith.domain.review.ReviewEntity;
import com.puremadeleine.viewith.domain.review.ReviewReportEntity;
import com.puremadeleine.viewith.domain.venue.SeatEntity;
import com.puremadeleine.viewith.domain.venue.VenueEntity;
import com.puremadeleine.viewith.dto.common.SortType;
import com.puremadeleine.viewith.dto.review.*;
import com.puremadeleine.viewith.exception.ViewithException;
import com.puremadeleine.viewith.provider.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.puremadeleine.viewith.converter.review.ReviewServiceConverter.toReviewListResDto;
import static com.puremadeleine.viewith.exception.ViewithErrorCode.PERMISSION_DENIED_FOR_REVIEW;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewService {

    ReviewProvider reviewProvider;
    VenueProvider venueProvider;
    SeatProvider seatProvider;
    ReviewReportProvider reviewReportProvider;
    MemberProvider memberProvider;
    ReviewServiceMapper mapper;

    @Transactional
    public CreateReviewResDto createReview(CreateReviewReqDto reqDto, Long memberId) {
        MemberEntity activeMember = memberProvider.getActiveMember(memberId);
        VenueEntity venue = venueProvider.getVenue(reqDto.getVenueId());
        SeatEntity seat = seatProvider.getSeat(reqDto.getSection(), reqDto.getSeatRow(), reqDto.getSeatColumn());

        ReviewEntity review = ReviewEntity.createReview(reqDto, venue, seat, activeMember);
        ReviewEntity savedReview = reviewProvider.saveReview(review);
        return CreateReviewResDto.builder().reviewId(savedReview.getId()).build();
    }

    @Transactional
    public void updateReview(Long reviewId, UpdateReviewReqDto reqDto, Long memberId) {
        ReviewEntity review = reviewProvider.getNormalReview(reviewId);
        checkPermission(review.getMember().getId(), memberId);
        review.updateReview(reqDto);
    }

    @Transactional
    public void deleteReview(Long reviewId, Long memberId) {
        ReviewEntity review = reviewProvider.getNormalReview(reviewId);
        checkPermission(review.getMember().getId(), memberId);
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
        return toReviewListResDto(isSummary, reviewList);
    }

    public void reportReview(Long reviewId, ReportReviewReqDto req, Long memberId) {
        ReviewEntity review = reviewProvider.getNormalReview(reviewId);
        if (review.getMember().getId().equals(memberId)) throw new ViewithException(PERMISSION_DENIED_FOR_REVIEW);
        review.reportReview();
        ReviewReportEntity reviewReport =
                ReviewReportEntity.createReviewReport(review, req.getReportReason(), req.getReportReasonDetail());
        reviewReportProvider.saveReviewReport(reviewReport);
    }


    private void checkPermission(Long reviewWriterId, Long reqMemberId) {
        if (!reviewWriterId.equals(reqMemberId)) {
            throw new ViewithException(PERMISSION_DENIED_FOR_REVIEW);
        }
    }

    @Mapper(componentModel = "spring")
    public interface ReviewServiceMapper {

        @Mapping(source = "id", target = "reviewId")
        @Mapping(source = "member", target = "userInfo")
        @Mapping(source = "member.id", target = "userInfo.userId")
        @Mapping(source = "member.nickname", target = "userInfo.userNickname")
        ReviewInfoResDto toReviewInfoResDto(ReviewEntity review);
    }

}