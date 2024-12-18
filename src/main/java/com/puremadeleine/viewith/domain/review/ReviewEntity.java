package com.puremadeleine.viewith.domain.review;

import com.puremadeleine.viewith.domain.BaseTimeEntity;
import com.puremadeleine.viewith.domain.member.MemberEntity;
import com.puremadeleine.viewith.domain.venue.PerformanceEntity;
import com.puremadeleine.viewith.domain.venue.SeatEntity;
import com.puremadeleine.viewith.domain.venue.VenueEntity;
import com.puremadeleine.viewith.dto.review.CreateReviewReqDto;
import com.puremadeleine.viewith.dto.review.UpdateReviewReqDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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

    @Enumerated(EnumType.STRING)
    Block block;

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
                .block(reqDto.getBlock())
                .member(member)
                .build();
    }

    public void updateReview(UpdateReviewReqDto reqDto) {
        this.setContent(reqDto.getContent());
        this.setRating(reqDto.getRating());
    }

    public void deleteReview() {
        this.setStatus(Status.DELETED);
    }

    public void reportReview() {
        // todo : report count final 변수로 수정
        reportCount += 1;
        if (reportCount >= 5) {
            this.setStatus(Status.REPORTED);
        }
    }
}
