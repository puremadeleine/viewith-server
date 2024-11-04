package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.domain.venue.VenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueRepository extends JpaRepository<VenueEntity, Long> {
}
