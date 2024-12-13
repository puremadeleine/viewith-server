package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.domain.venue.VenueStageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VenueStageRepository extends JpaRepository<VenueStageEntity, Long> {
    List<VenueStageEntity> findAllByVenue_Id(long venueId);
}
