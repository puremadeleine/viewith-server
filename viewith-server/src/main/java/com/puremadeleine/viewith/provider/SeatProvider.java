package com.puremadeleine.viewith.provider;

import com.puremadeleine.viewith.domain.venue.SeatEntity;
import com.puremadeleine.viewith.exception.ViewithException;
import com.puremadeleine.viewith.repository.SeatRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import static com.puremadeleine.viewith.exception.ViewithErrorCode.NO_SEAT;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeatProvider {

    final SeatRepository seatRepository;

    public SeatEntity getSeat(String section, Integer seatRow, Integer seatColumn) {
        return seatRepository.findBySectionAndSeatRowAndSeatColumn(section,seatRow, seatColumn)
                .orElseThrow(() ->
                        new ViewithException(NO_SEAT, "The seat with section: " + section + ", seat_row: " + seatRow +
                                ", seat_column: " + seatColumn + " was not found.")
                );
    }

}
