package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.domain.venue.VenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<VenueEntity, Long> {
}
