package com.puremadeleine.viewith.service;

import com.puremadeleine.viewith.domain.venue.PerformanceEntity;
import com.puremadeleine.viewith.domain.venue.VenueEntity;
import com.puremadeleine.viewith.dto.client.kopis.PerformanceDetailResDto;
import com.puremadeleine.viewith.dto.client.kopis.PerformanceInfoResDto;
import com.puremadeleine.viewith.provider.KopisProvider;
import com.puremadeleine.viewith.provider.PerformanceProvider;
import com.puremadeleine.viewith.provider.VenueProvider;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.puremadeleine.viewith.converter.performance.UpcomingPerformanceServiceConverter.toPerformance;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UpcomingPerformanceService {

    static final String GENRE_CODE = "CCCD";

    final KopisProvider kopisProvider;
    final PerformanceProvider performanceProvider;
    final VenueProvider venueProvider;

    public void saveUpcomingPerformances(String startDate, String endDate, int page, int size) {

        List<VenueEntity> venues = venueProvider.getVenues();
        List<PerformanceEntity> allPerformances = getAllPerformances(venues, startDate, endDate, page, size);

        performanceProvider.saveAll(allPerformances);
    }

    private List<PerformanceEntity> getAllPerformances(List<VenueEntity> venues, String startDate, String endDate, int page, int size) {
        List<PerformanceEntity> performanceEntityList = new ArrayList<>();
        venues.forEach(v -> {
            List<PerformanceDetailResDto> performances = getPerformances(v, startDate, endDate, page, size);
            List<PerformanceEntity> list = performances.stream()
                    .map(p -> toPerformance(p, v))
                    .toList();

            performanceEntityList.addAll(list);
        });

        return performanceEntityList;
    }

    private List<PerformanceDetailResDto> getPerformances(VenueEntity venue, String startDate, String endDate, int page, int size) {
        List<PerformanceInfoResDto> performances = getPerformances(venue.getVenueCode(), startDate, endDate, page, size);

        List<String> ids = performances.stream()
                .map(PerformanceInfoResDto::getId)
                .toList();

        return ids.stream()
                .map(this::getPerformanceDetails)
                .flatMap(List::stream)
                .toList();
    }

    public List<PerformanceInfoResDto> getPerformances(String venueCode, String startDate, String endDate, int page, int size) {
        return kopisProvider.getPerformanceList(venueCode, GENRE_CODE, startDate, endDate, page, size);
    }

    public List<PerformanceDetailResDto> getPerformanceDetails(String performanceId) {
        return kopisProvider.getPerformanceDetails(performanceId);
    }
}
