package com.puremadeleine.viewith.provider;

import com.puremadeleine.viewith.domain.venue.SeatEntity;
import com.puremadeleine.viewith.exception.ViewithException;
import com.puremadeleine.viewith.repository.SeatRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.puremadeleine.viewith.exception.ViewithErrorCode.NO_NORMAL_REVIEW;
import static com.puremadeleine.viewith.exception.ViewithErrorCode.NO_SEAT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SeatProviderTest {

    SeatRepository seatRepository = mock(SeatRepository.class);

    SeatProvider seatProvider = new SeatProvider(seatRepository);

    @DisplayName("return the seatEntity when the seat is successfully returned")
    @Test
    void return_seatEntity_successfully_test() {
        // given
        SeatEntity seat = SeatEntity.builder()
                .id(1L)
                .section("A")
                .seatRow(1)
                .seatColumn(1)
                .build();

        when(seatRepository.findBySectionAndSeatRowAndSeatColumn(anyString(), anyInt(), anyInt()))
                .thenReturn(Optional.ofNullable(seat));

        // when
        SeatEntity result = seatProvider.getSeat("A", 1, 1);

        // then
        assertThat(result.getId()).isEqualTo(1L);
    }

    @DisplayName("throw exception when the seat retrieval fails")
    @Test
    void throw_exception_when_return_null() {
        // given
        when(seatRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        ViewithException result = assertThrows(ViewithException.class, () -> seatProvider.getSeat("A", 1, 1));

        // then
        assertThat(result.getErrorCode().getCode()).isEqualTo(NO_SEAT.getCode());
    }

}