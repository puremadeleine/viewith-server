package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.domain.bookmark.BookmarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<BookmarkEntity, Long> {
    @Query("SELECT COUNT(b) FROM BookmarkEntity b WHERE b.member.id = :memberId")
    long countByMemberId(@Param("memberId") Long memberId);

    Optional<BookmarkEntity> findByMember_IdAndSeat_Id(Long memberId, Long seatId);
}
