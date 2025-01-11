package com.puremadeleine.viewith.service;

import com.puremadeleine.viewith.domain.image.ImageEntity;
import com.puremadeleine.viewith.domain.image.SourceType;
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
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

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
    ImageService imageService;
    ReviewServiceMapper mapper;

    @Transactional
    public CreateReviewResDto createReview(CreateReviewReqDto reqDto, List<MultipartFile> images, Long memberId) {
        MemberEntity activeMember = memberProvider.getActiveMember(memberId);
        VenueEntity venue = venueProvider.getVenue(reqDto.getVenueId());
        SeatEntity seat = seatProvider.getSeat(reqDto.getSection(), reqDto.getSeatRow(), reqDto.getSeatColumn());

        ReviewEntity review = ReviewEntity.createReview(reqDto, venue, seat, activeMember);
        ReviewEntity savedReview = reviewProvider.saveReview(review);
        imageService.saveImages(images, review.getId(), SourceType.REVIEW);

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
        List<String> imageUrls = imageService.getReviewImageUrlList(reviewId);
        return mapper.toReviewInfoResDto(review, imageUrls);
    }

    public ReviewListResDto getReviewList(ReviewListReqDto req, boolean isSummary) {
        Page<ReviewEntity> reviewList = (SortType.DEFAULT.equals(req.getSortType()))
                ? reviewProvider.getReviewListPrioritizingMedia(req)
                : reviewProvider.getReviewList(req);
        List<Long> reviewIds = reviewList.getContent().stream().map(ReviewEntity::getId).toList();
        Map<Long, List<String>> reviewImageUrlMap = imageService.getReviewImageUrlMap(reviewIds);
        return toReviewListResDto(isSummary, reviewList, reviewImageUrlMap);
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

        @Mapping(source = "review.id", target = "reviewId")
        @Mapping(source = "review.member.id", target = "userInfo.userId")
        @Mapping(source = "review.member.nickname", target = "userInfo.userNickname")
        @Mapping(source = "review.seat.floor", target = "seatInfo.floor")
        @Mapping(source = "review.seat.section", target = "seatInfo.section")
        @Mapping(source = "review.seat.seatRow", target = "seatInfo.seatRow")
        @Mapping(source = "review.seat.seatColumn", target = "seatInfo.seatColumn")
        @Mapping(source = "review.seat.block", target = "seatInfo.block")
        @Mapping(source = "imageList", target = "imageList")
        ReviewInfoResDto toReviewInfoResDto(ReviewEntity review, List<String> imageList);
    }

}