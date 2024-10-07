package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.domain.venue.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<SeatEntity, Long> {

    Optional<SeatEntity> findBySectionAndSeatRowAndSeatColumn(String section, Integer seatRow, Integer seatColumn);
}
