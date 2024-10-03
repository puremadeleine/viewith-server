package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.domain.review.Review;
import com.puremadeleine.viewith.domain.review.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

     Optional<Review> findByIdAndStatus(Long reviewId, Status status);
}
