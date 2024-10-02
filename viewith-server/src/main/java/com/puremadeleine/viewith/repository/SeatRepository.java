package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.domain.venue.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    Optional<Seat> findBySectionAndSeatRowAndSeatColumn(String section, Integer seatRow, Integer seatColumn);
}
