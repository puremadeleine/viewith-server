package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.domain.review.ReviewEntity;
import com.puremadeleine.viewith.domain.review.Status;
import com.puremadeleine.viewith.dto.review.ReviewCntDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    Optional<ReviewEntity> findByIdAndStatus(Long reviewId, Status status);


    @Query("""
            SELECT new com.puremadeleine.viewith.dto.review.ReviewCntDto(COUNT(r), s.floor, s.section)
            FROM ReviewEntity r JOIN r.seat s
            WHERE r.venue.id = :venueId AND r.status = :status
            GROUP BY s.floor, s.section
            """)
    List<ReviewCntDto> countReviewsBySeat(@Param("venueId") Long venueId,
                                          @Param("status") Status status);
}
