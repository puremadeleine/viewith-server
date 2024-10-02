package com.puremadeleine.viewith.domain.review;

import com.puremadeleine.viewith.domain.BaseTimeEntity;
import com.puremadeleine.viewith.domain.venue.Performance;
import com.puremadeleine.viewith.domain.venue.Seat;
import com.puremadeleine.viewith.domain.venue.Venue;
import com.puremadeleine.viewith.dto.review.CreateReviewReqDto;
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
public class Review extends BaseTimeEntity {

    @Id @GeneratedValue()
    @Column(name = "review_id")
    Long id;

    String content;
    Float rating;

    @Enumerated(EnumType.STRING)
    Status status;

    Integer reportCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @Enumerated(EnumType.STRING)
    Block block;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private Performance performance;

    // todo : member info 추가

    public static Review createReview(CreateReviewReqDto reqDto, Venue venue, Seat seat) {
        return Review.builder()
                .content(reqDto.getContent())
                .rating(reqDto.getRating())
                .status(Status.NORMAL)
                .reportCount(0)
                .venue(venue)
                .seat(seat)
                .block(reqDto.getBlock())
                .build();
    }


}
