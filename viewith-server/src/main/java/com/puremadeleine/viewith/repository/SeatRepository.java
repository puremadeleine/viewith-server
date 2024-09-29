package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.domain.venue.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
}
