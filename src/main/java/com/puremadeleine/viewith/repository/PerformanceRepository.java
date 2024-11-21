package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.domain.venue.PerformanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceRepository extends JpaRepository<PerformanceEntity, Long> {

     List<PerformanceEntity> findByTitleContainsIgnoreCase(String keyword);
}
