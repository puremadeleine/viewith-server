package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.domain.review.ReviewReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewReportRepository extends JpaRepository<ReviewReportEntity, Long> {
}
