package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.config.jpa.JpaConfig;
import com.puremadeleine.viewith.domain.bookmark.BookmarkEntity;
import com.puremadeleine.viewith.domain.member.MemberEntity;
import com.puremadeleine.viewith.domain.venue.SeatEntity;
import com.puremadeleine.viewith.domain.venue.VenueEntity;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;
import java.util.Random;

import static com.puremadeleine.viewith.dto.member.OAuthType.KAKAO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

@DataJpaTest
@Import(JpaConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookmarkRepositoryTest {

    @Autowired
    BookmarkRepository bookmarkRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    VenueRepository venueRepository;
    @Autowired
    SeatRepository seatRepository;

    @Test
    void find() {
        // given
        MemberEntity member = makeDummyMemberEntity();
        member = memberRepository.saveAndFlush(member);
        VenueEntity venue = makeDummyVenueEntity();
        venue = venueRepository.saveAndFlush(venue);
        SeatEntity seat = makeDummySeatEntity(venue);
        seat = seatRepository.saveAndFlush(seat);
        BookmarkEntity bookmark = makeDummyBookmarkEntity(member, seat);
        bookmark = bookmarkRepository.saveAndFlush(bookmark);

        // when
        Optional<BookmarkEntity> actual = bookmarkRepository.findById(bookmark.getId());

        // then
        assertThat(actual).isPresent();
        assertThat(actual.get()).usingRecursiveComparison().isEqualTo(bookmark);
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

    private BookmarkEntity makeDummyBookmarkEntity(MemberEntity member, SeatEntity seat) {
        return Instancio.of(BookmarkEntity.class)
                .ignore(field(BookmarkEntity::getId))
                .set(field(BookmarkEntity::getMember), member)
                .set(field(BookmarkEntity::getSeat), seat)
                .create();
    }
}