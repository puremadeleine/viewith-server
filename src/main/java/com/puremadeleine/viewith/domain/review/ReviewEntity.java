package com.puremadeleine.viewith.domain.review;

import com.puremadeleine.viewith.domain.BaseTimeEntity;
import com.puremadeleine.viewith.domain.member.MemberEntity;
import com.puremadeleine.viewith.domain.venue.PerformanceEntity;
import com.puremadeleine.viewith.domain.venue.SeatEntity;
import com.puremadeleine.viewith.domain.venue.VenueEntity;
import com.puremadeleine.viewith.dto.review.request.CreateReviewReqDto;
import com.puremadeleine.viewith.dto.review.request.UpdateReviewReqDto;
import com.puremadeleine.viewith.exception.ViewithException;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import static com.puremadeleine.viewith.constants.CommonConstants.REPORT_THRESHOLD;
import static com.puremadeleine.viewith.exception.ViewithErrorCode.PERMISSION_DENIED_FOR_REVIEW;

@Entity
@Table(name = "tb_review")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    Long id;

    @Setter(value = AccessLevel.PRIVATE)
    String content;

    @Setter(value = AccessLevel.PRIVATE)
    Float rating;

    @Setter(value = AccessLevel.PRIVATE)
    @Enumerated(EnumType.STRING)
    Status status;

    Integer reportCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id")
    VenueEntity venue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    SeatEntity seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    PerformanceEntity performance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    MemberEntity member;

    public static ReviewEntity createReview(CreateReviewReqDto reqDto, VenueEntity venue,
                                            SeatEntity seat, MemberEntity member) {
        return ReviewEntity.builder()
                .content(reqDto.getContent())
                .rating(reqDto.getRating())
                .status(Status.NORMAL)
                .reportCount(0)
                .venue(venue)
                .seat(seat)
                .member(member)
                .build();
    }

    public void updateReview(UpdateReviewReqDto reqDto, Long reqMemberId) {
        validateEditable(reqMemberId);
        this.setContent(reqDto.getContent());
        this.setRating(reqDto.getRating());
    }

    public void deleteReview(Long reqMemberId) {
        validateEditable(reqMemberId);
        this.setStatus(Status.DELETED);
    }

    public void reportReview(Long reqMemberId) {
        validateReportable(reqMemberId);
        reportCount += 1;
        if (reportCount >= REPORT_THRESHOLD) {
            this.setStatus(Status.REPORTED);
        }
    }

    private void validateEditable(Long reqMemberId) {
        if (!member.getId().equals(reqMemberId)) {
            throw new ViewithException(PERMISSION_DENIED_FOR_REVIEW);
        }
    }

    public void validateReportable(Long reqMemberId) {
        if (member.getId().equals(reqMemberId)) {
            throw new ViewithException(PERMISSION_DENIED_FOR_REVIEW);
        }
    }
}
