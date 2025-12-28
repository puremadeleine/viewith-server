package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.domain.bookmark.BookmarkEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<BookmarkEntity, Long> {
    @Query("SELECT COUNT(b) FROM BookmarkEntity b WHERE b.member.id = :memberId")
    long countByMemberId(@Param("memberId") Long memberId);

    @EntityGraph(attributePaths = "seat")
    List<BookmarkEntity> findByMemberId(Long memberId);

    @Query(value = """
            SELECT b
            FROM BookmarkEntity b JOIN FETCH SeatEntity s ON s.id = b.seat.id
            WHERE b.member.id = :memberId AND s.venue.id = :venueId
            """)
    List<BookmarkEntity> findBookmarksByVenueIdAndMemberId(@Param("venueId") Long venueId, @Param("memberId") Long memberId);

    Optional<BookmarkEntity> findByMember_IdAndSeat_Id(Long memberId, Long seatId);

    @EntityGraph(attributePaths = "member")
    @Query("SELECT b FROM BookmarkEntity b WHERE b.id IN :bookmarkIds")
    List<BookmarkEntity> findAllByIdWithMember(@Param("bookmarkIds") List<Long> bookmarkIds);

    @Query("SELECT COUNT(b) > 0 FROM BookmarkEntity b WHERE b.seat.id = :seatId AND b.member.id = :memberId")
    Boolean existsBySeatIdAndMemberId(@Param("seatId") Long seatId, @Param("memberId") Long memberId);
}
