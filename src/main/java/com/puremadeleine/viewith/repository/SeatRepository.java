package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.domain.venue.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<SeatEntity, Long> {

    Optional<SeatEntity> findBySectionAndSeatRowAndSeatColumn(String section, Integer seatRow, Integer seatColumn);

    List<SeatEntity> findAllByVenue_Id(long venueId);
}
