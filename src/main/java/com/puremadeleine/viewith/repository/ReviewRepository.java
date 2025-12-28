package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.domain.review.ReviewEntity;
import com.puremadeleine.viewith.domain.review.Status;
import com.puremadeleine.viewith.dto.review.ReviewCntDto;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    Optional<ReviewEntity> findByIdAndStatus(Long reviewId, Status status);


    @Query("""
            SELECT r.createTime
            FROM ReviewEntity r JOIN SeatEntity s ON r.seat.id = s.id
            WHERE s.venue.id = :venueId
            AND s.floor = :floor
            AND s.section = :section
            AND (:row IS NULL OR r.seat.seatRow = :row)
            AND r.status = :status
            ORDER BY r.createTime DESC
            """)
    List<LocalDateTime> findTopReviewsBySeatInfoAndStatus(@Param("venueId") Long venueId,
                                                          @Param("floor") String floor,
                                                          @Param("section") String section,
                                                          @Nullable @Param("row") String row,
                                                          @Param("status") Status status,
                                                          Pageable pageable);

    @Query("""
            SELECT new com.puremadeleine.viewith.dto.review.ReviewCntDto(COUNT(r), s.floor, s.section)
            FROM ReviewEntity r JOIN r.seat s
            WHERE r.venue.id = :venueId AND r.status = :status
            GROUP BY s.floor, s.section
            """)
    List<ReviewCntDto> countReviewsBySeat(@Param("venueId") Long venueId,
                                          @Param("status") Status status);


    @Query("SELECT COUNT(r) FROM ReviewEntity r WHERE r.member.id = :memberId AND r.status = :status")
    long countReviewsByMember(@Param("memberId") Long memberId,
                              @Param("status") Status status);
}
