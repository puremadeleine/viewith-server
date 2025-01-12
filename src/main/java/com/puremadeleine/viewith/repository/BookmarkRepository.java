package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.domain.bookmark.BookmarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookmarkRepository extends JpaRepository<BookmarkEntity, Long> {
    @Query("SELECT COUNT(b) FROM BookmarkEntity b WHERE b.member.id = :memberId")
    long countByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT COUNT(b) > 0 FROM BookmarkEntity b WHERE b.seat.id = :seatId AND b.member.id = :memberId")
    Boolean existsBySeatIdAndMemberId(@Param("seatId") Long seatId, @Param("memberId") Long memberId);
}
