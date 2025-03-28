package com.puremadeleine.viewith.service;

import com.puremadeleine.viewith.domain.venue.PerformanceEntity;
import com.puremadeleine.viewith.domain.venue.VenueEntity;
import com.puremadeleine.viewith.dto.client.kopis.PerformanceDetailResDto;
import com.puremadeleine.viewith.dto.client.kopis.PerformanceInfoResDto;
import com.puremadeleine.viewith.provider.KopisProvider;
import com.puremadeleine.viewith.provider.PerformanceProvider;
import com.puremadeleine.viewith.provider.VenueProvider;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UpcomingPerformanceServiceTest {

    KopisProvider kopisProvider = mock(KopisProvider.class);
    PerformanceProvider performanceProvider = mock(PerformanceProvider.class);
    VenueProvider venueProvider = mock(VenueProvider.class);

    UpcomingPerformanceService upcomingPerformanceService = new UpcomingPerformanceService(
            kopisProvider,
            performanceProvider,
            venueProvider
    );

    @DisplayName("")
    @Test
    void test() {
        // given
        List<VenueEntity> venueEntities = makeDummyVenueEntity();
        when(venueProvider.getVenues()).thenReturn(venueEntities);

        List<PerformanceInfoResDto> performanceInfoResDtos = makeDummyPerformanceInfoResDtos();
        when(kopisProvider.getPerformanceList(anyString(),anyString(), anyString(),anyString(), anyInt(), anyInt()))
                .thenReturn(performanceInfoResDtos);

        List<String> allKopisIds = performanceInfoResDtos.stream().map(PerformanceInfoResDto::getId).toList();
        allKopisIds.forEach(
                id -> when(kopisProvider.getPerformanceDetails(eq(id)))
                        .thenReturn(List.of(makePerformanceDetail(id)))
        );

        when(performanceProvider.getExistKopisIds(anyList())).thenReturn(List.of("1", "2"));

        int size = 3;
        when(performanceProvider.saveAll(anyList())).thenReturn(makeDummyPerformanceEntities(size));

        // when, // then
        List<PerformanceEntity> result = upcomingPerformanceService.saveUpcomingPerformances("20250101", "20250101", 1, 10);

        assertEquals(result.size(), size);
    }

    private List<VenueEntity> makeDummyVenueEntity() {
        return Instancio.ofList(VenueEntity.class)
                .size(2)
                .ignore(field(VenueEntity::getId))
                .create();
    }

    private List<PerformanceInfoResDto> makeDummyPerformanceInfoResDtos() {
        return Instancio.ofList(PerformanceInfoResDto.class)
                .size(3)
                .create();
    }

    private PerformanceDetailResDto makePerformanceDetail(String kopisId) {
        return Instancio.of(PerformanceDetailResDto.class)
                .set(field(PerformanceDetailResDto::getId), kopisId)
                .set(field(PerformanceDetailResDto::getStartDate),
                        LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                .set(field(PerformanceDetailResDto::getEndDate),
                        LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                .create();
    }

    private List<PerformanceEntity> makeDummyPerformanceEntities(int size) {
        return Instancio.ofList(PerformanceEntity.class)
                .size(size)
                .create();
    }
}
