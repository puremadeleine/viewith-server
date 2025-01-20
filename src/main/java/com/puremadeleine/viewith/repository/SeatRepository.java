package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.domain.venue.SeatEntity;
import com.puremadeleine.viewith.dto.venue.FloorRowDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<SeatEntity, Long> {

    Optional<SeatEntity> findBySectionAndSeatRowAndSeatColumn(String section, Integer seatRow, Integer seatColumn);

    List<SeatEntity> findAllByVenue_Id(long venueId);

    @Query("""
            SELECT distinct new com.puremadeleine.viewith.dto.venue.FloorRowDto(s.floor, s.seatRow)
            FROM SeatEntity s
            WHERE s.venue.id = :venueId
            """)
    List<FloorRowDto> findAllSeatsByVenueId(@Param("venueId") Long venueId);
}
