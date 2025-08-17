package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.config.jpa.JpaConfig;
import com.puremadeleine.viewith.domain.image.ImageEntity;
import com.puremadeleine.viewith.domain.image.SourceType;
import com.puremadeleine.viewith.domain.member.MemberEntity;
import com.puremadeleine.viewith.domain.review.ReviewEntity;
import com.puremadeleine.viewith.domain.review.Status;
import com.puremadeleine.viewith.domain.venue.PerformanceEntity;
import com.puremadeleine.viewith.domain.venue.SeatEntity;
import com.puremadeleine.viewith.domain.venue.VenueEntity;
import com.puremadeleine.viewith.dto.common.SortType;
import com.puremadeleine.viewith.dto.review.request.ReviewListReqDto;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static com.puremadeleine.viewith.dto.common.SortType.LATEST;
import static com.puremadeleine.viewith.dto.member.OAuthType.KAKAO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

@ActiveProfiles("test")
@DataJpaTest
@Import({JpaConfig.class, ReviewCustomRepository.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReviewCustomRepositoryTest {

    @Autowired
    ReviewCustomRepository reviewCustomRepository;
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
    @Autowired
    ImageRepository imageRepository;


    @DisplayName("LATEST findMyReviewList")
    @Test
    void findMyReviewList() {
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

        ReviewEntity review1 = makeDummyReviewEntity(venue, seat, performance, member, Status.NORMAL, 1);
        ReviewEntity review5 = makeDummyReviewEntity(venue, seat, performance2, member, Status.NORMAL, 20);
        ReviewEntity review4 = makeDummyReviewEntity(venue, seat, performance2, member, Status.DELETED, 15);
        ReviewEntity review3 = makeDummyReviewEntity(venue, seat, performance, member2, Status.NORMAL, 10);
        ReviewEntity review2 = makeDummyReviewEntity(venue, seat, performance, member, Status.REPORTED, 5);
        reviewRepository.saveAllAndFlush(List.of(review1, review4, review3, review2, review5));

        ReviewListReqDto req = ReviewListReqDto.builder()
                .page(1)
                .size(10)
                .sortType(LATEST)
                .build();

        // when
        List<ReviewEntity> actual = reviewCustomRepository.findMyReviewList(member.getId(), req);

        // then
        assertThat(actual).isNotEmpty()
                .hasSize(2);
        assertThat(actual.get(0)).usingRecursiveComparison().isEqualTo(review5);
        assertThat(actual.get(1)).usingRecursiveComparison().isEqualTo(review1);
    }

    @DisplayName("DEFAULT findMyReviewList")
    @Test
    void findMyReviewListPrioritizingMedia() {
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

        ReviewEntity review1 = makeDummyReviewEntity(venue, seat, performance, member, Status.NORMAL, 1);
        ReviewEntity review5 = makeDummyReviewEntity(venue, seat, performance2, member, Status.NORMAL, 20);
        ReviewEntity review4 = makeDummyReviewEntity(venue, seat, performance2, member, Status.DELETED, 15);
        ReviewEntity review3 = makeDummyReviewEntity(venue, seat, performance, member2, Status.NORMAL, 10);
        ReviewEntity review2 = makeDummyReviewEntity(venue, seat, performance, member, Status.REPORTED, 5);
        ReviewEntity review6 = makeDummyReviewEntity(venue, seat, performance, member, Status.REPORTED, 7);
        List<ReviewEntity> reviews = List.of(review1, review6, review4, review3, review2, review5);
        reviewRepository.saveAllAndFlush(reviews);

        ImageEntity image1ByReview1 = makeDummyImageEntity(review1.getId(), SourceType.REVIEW, 30);
        ImageEntity image2ByReview1 = makeDummyImageEntity(review4.getId(), SourceType.REVIEW, 20);
        ImageEntity image1ByReview6 = makeDummyImageEntity(review6.getId(), SourceType.REVIEW, 25);
        ImageEntity image = makeDummyImageEntity(review2.getId(), SourceType.HELP, 10);
        imageRepository.saveAllAndFlush(List.of(image1ByReview1, image2ByReview1, image, image1ByReview6));

        ReviewListReqDto req = ReviewListReqDto.builder()
                .page(1)
                .size(10)
                .sortType(SortType.DEFAULT)
                .build();

        // when
        Long memberId = member.getId();
        List<ReviewEntity> actual = reviewCustomRepository.findMyReviewListPrioritizingMedia(memberId, req);

        // then
        assertThat(actual).isNotEmpty()
                .hasSize(2);

        List<ReviewEntity> photoReviews = sorted(List.of(review1), memberId);
        List<ReviewEntity> noPhotoReviews = sorted(List.of(review5, review2), memberId);

        assertThat(actual.get(0)).usingRecursiveComparison().isEqualTo(photoReviews.get(0));
        assertThat(actual.get(1)).usingRecursiveComparison().isEqualTo(noPhotoReviews.get(0));
    }

    private MemberEntity makeDummyMemberEntity() {
        Long oauthId = new Random().nextLong();
        return Instancio.of(MemberEntity.class)
                .ignore(field(MemberEntity::getId))
                .ignore(field(MemberEntity::getProfileImage))
                .set(field(MemberEntity::getOauthType), KAKAO)
                .set(field(MemberEntity::getDeleteYn), false)
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

    private ReviewEntity makeDummyReviewEntity(VenueEntity venue, SeatEntity seat, PerformanceEntity performance, MemberEntity member, Status status, int index) {
        return Instancio.of(ReviewEntity.class)
                .ignore(field(ReviewEntity::getId))
                .set(field(ReviewEntity::getVenue), venue)
                .set(field(ReviewEntity::getSeat), seat)
                .set(field(ReviewEntity::getPerformance), performance)
                .set(field(ReviewEntity::getMember), member)
                .set(field(ReviewEntity::getStatus), status)
                .set(field(ReviewEntity::getCreateTime), LocalDateTime.of(2025, 3, index, 0, 0))
                .create();
    }

    private ImageEntity makeDummyImageEntity(Long sourceId, SourceType sourceType, int index) {
        return Instancio.of(ImageEntity.class)
                .ignore(field(ImageEntity::getId))
                .ignore(field(ImageEntity::getImageUrl))
                .set(field(ImageEntity::getSourceId), sourceId)
                .set(field(ImageEntity::getSourceType), sourceType)
                .set(field(ImageEntity::getCreateTime), LocalDateTime.of(2025, 3, index, 0, 0))
                .create();
    }

    private List<ReviewEntity> sorted(List<ReviewEntity> reviews, Long memberId) {
        return reviews.stream()
                .filter(r -> memberId.equals(r.getMember().getId()))
                .filter(r -> Status.DELETED != r.getStatus())
                .sorted(Comparator.comparing(ReviewEntity::getId).reversed())
                .toList();
    }
}