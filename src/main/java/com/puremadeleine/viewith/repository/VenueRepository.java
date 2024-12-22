package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.domain.venue.VenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VenueRepository extends JpaRepository<VenueEntity, Long> {
    List<VenueEntity> findByNameContainsIgnoreCase(String keyword);
}
