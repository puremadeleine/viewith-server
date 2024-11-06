package com.puremadeleine.viewith.provider;

import com.puremadeleine.viewith.domain.venue.VenueEntity;
import com.puremadeleine.viewith.exception.ViewithException;
import com.puremadeleine.viewith.repository.VenueRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.puremadeleine.viewith.exception.ViewithErrorCode.NO_SEAT;
import static com.puremadeleine.viewith.exception.ViewithErrorCode.NO_VENUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VenueProviderTest {

    VenueRepository venueRepository = mock(VenueRepository.class);

    VenueProvider venueProvider = new VenueProvider(venueRepository);

    @DisplayName("return the venueEntity when the venue is successfully returned")
    @Test
    void return_venueEntity_successfully_test() {
        // given
        VenueEntity venue = VenueEntity.builder()
                .id(1L)
                .name("venue1")
                .build();

        when(venueRepository.findById(anyLong())).thenReturn(Optional.ofNullable(venue));

        // when
        VenueEntity result = venueProvider.getVenue(1L);

        // then
        assertThat(result.getId()).isEqualTo(1L);
    }

    @DisplayName("throw exception when the venue retrieval fails")
    @Test
    void throw_exception_when_return_null() {
        // given
        when(venueRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        ViewithException result = assertThrows(ViewithException.class, () -> venueProvider.getVenue(1L));

        // then
        assertThat(result.getErrorCode().getCode()).isEqualTo(NO_VENUE.getCode());
    }

}