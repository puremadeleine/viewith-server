package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.config.jpa.JpaConfig;
import com.puremadeleine.viewith.domain.member.MemberEntity;
import com.puremadeleine.viewith.domain.review.ReviewEntity;
import com.puremadeleine.viewith.domain.review.Status;
import com.puremadeleine.viewith.domain.venue.PerformanceEntity;
import com.puremadeleine.viewith.domain.venue.SeatEntity;
import com.puremadeleine.viewith.domain.venue.VenueEntity;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Random;

import static com.puremadeleine.viewith.dto.member.OAuthType.KAKAO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

@ActiveProfiles("test")
@DataJpaTest
@Import(JpaConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReviewRepositoryTest {

    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    VenueRepository venueRepository;
    @Autowired
    SeatRepository seatRepository;
    @Autowired
    PerformanceRepository performanceRepository;

    @Test
    void countReviewsByMember() {
        // given
        MemberEntity member = makeDummyMemberEntity();
        member = memberRepository.saveAndFlush(member);
        MemberEntity member2 = makeDummyMemberEntity();
        member2 = memberRepository.saveAndFlush(member2);

        VenueEntity venue = makeDummyVenueEntity();
        venue = venueRepository.saveAndFlush(venue);
        SeatEntity seat = makeDummySeatEntity(venue);
        seat = seatRepository.saveAndFlush(seat);
        PerformanceEntity performance = makePerformanceEntity(venue);
        performance = performanceRepository.saveAndFlush(performance);
        PerformanceEntity performance2 = makePerformanceEntity(venue);
        performance2 = performanceRepository.saveAndFlush(performance2);

        ReviewEntity review = makeDummyReviewEntity(venue, seat, performance, member, Status.NORMAL);
        ReviewEntity review2 = makeDummyReviewEntity(venue, seat, performance2, member, Status.NORMAL);
        ReviewEntity review3 = makeDummyReviewEntity(venue, seat, performance2, member, Status.DELETED);
        ReviewEntity review4 = makeDummyReviewEntity(venue, seat, performance, member2, Status.NORMAL);
        reviewRepository.saveAllAndFlush(List.of(review, review2, review3, review4));

        // when
        long actual = reviewRepository.countReviewsByMember(member.getId(), Status.NORMAL);

        // then
        assertThat(actual).isEqualTo(2L);
    }

    private MemberEntity makeDummyMemberEntity() {
        Long oauthId = new Random().nextLong();
        return Instancio.of(MemberEntity.class)
                .ignore(field(MemberEntity::getId))
                .set(field(MemberEntity::getOauthType), KAKAO)
                .set(field(MemberEntity::getDeleteYn), false)
                .set(field(MemberEntity::getOauthUserId), oauthId)
                .set(field(MemberEntity::getViewithOauthUserId), oauthId.toString())
                .create();
    }

    private VenueEntity makeDummyVenueEntity() {
        return Instancio.of(VenueEntity.class)
                .ignore(field(VenueEntity::getId))
                .create();
    }

    private SeatEntity makeDummySeatEntity(VenueEntity venue) {
        return Instancio.of(SeatEntity.class)
                .ignore(field(SeatEntity::getId))
                .set(field(SeatEntity::getVenue), venue)
                .create();
    }

    private PerformanceEntity makePerformanceEntity(VenueEntity venue) {
        return Instancio.of(PerformanceEntity.class)
                .ignore(field(PerformanceEntity::getId))
                .set(field(PerformanceEntity::getVenue), venue)
                .create();
    }

    private ReviewEntity makeDummyReviewEntity(VenueEntity venue, SeatEntity seat, PerformanceEntity performance, MemberEntity member, Status status) {
        return Instancio.of(ReviewEntity.class)
                .ignore(field(ReviewEntity::getId))
                .set(field(ReviewEntity::getVenue), venue)
                .set(field(ReviewEntity::getSeat), seat)
                .set(field(ReviewEntity::getPerformance), performance)
                .set(field(ReviewEntity::getMember), member)
                .set(field(ReviewEntity::getStatus), status)
                .create();
    }

}