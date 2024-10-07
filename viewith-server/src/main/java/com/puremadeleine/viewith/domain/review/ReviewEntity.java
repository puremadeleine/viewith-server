package com.puremadeleine.viewith.domain.review;

import com.puremadeleine.viewith.domain.BaseTimeEntity;
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

    @Id @GeneratedValue()
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
    private VenueEntity venue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private SeatEntity seat;

    @Enumerated(EnumType.STRING)
    Block block;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private PerformanceEntity performance;

    // todo : member info 추가

    public static ReviewEntity createReview(CreateReviewReqDto reqDto, VenueEntity venue, SeatEntity seat) {
        return ReviewEntity.builder()
                .content(reqDto.getContent())
                .rating(reqDto.getRating())
                .status(Status.NORMAL)
                .reportCount(0)
                .venue(venue)
                .seat(seat)
                .block(reqDto.getBlock())
                .build();
    }

    public void updateReview(UpdateReviewReqDto reqDto) {
        this.setContent(reqDto.getContent());
        this.setRating(reqDto.getRating());
    }

    public void deleteReview() {
        this.setStatus(Status.DELETED);
    }
}
