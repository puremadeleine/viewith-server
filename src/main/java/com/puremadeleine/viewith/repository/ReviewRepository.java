package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.domain.review.ReviewEntity;
import com.puremadeleine.viewith.domain.review.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

     Optional<ReviewEntity> findByIdAndStatus(Long reviewId, Status status);
}
