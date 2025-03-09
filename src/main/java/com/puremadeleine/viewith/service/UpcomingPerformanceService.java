package com.puremadeleine.viewith.service;

import com.puremadeleine.viewith.domain.venue.PerformanceEntity;
import com.puremadeleine.viewith.dto.client.kopis.PerformanceDetailResDto;
import com.puremadeleine.viewith.dto.client.kopis.PerformanceInfoResDto;
import com.puremadeleine.viewith.provider.KopisProvider;
import com.puremadeleine.viewith.provider.PerformanceProvider;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

import static com.puremadeleine.viewith.converter.performance.UpcomingPerformanceServiceConverter.toPerformance;
import static com.puremadeleine.viewith.domain.venue.VenueCode.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UpcomingPerformanceService {

    static final String GENRE_CODE = "CCCD";

    final KopisProvider kopisProvider;
    final PerformanceProvider performanceProvider;

    public void saveUpcomingPerformances(String startDate, String endDate, int page, int size) {
        List<PerformanceInfoResDto> kspoPerformances = getKSPOPerformances(startDate, endDate, page, size);
        List<PerformanceInfoResDto> jangchugPerformances = getJANGCHUGPerformances(startDate, endDate, page, size);
        List<PerformanceInfoResDto> jamsilPerformances = getJAMSILPerformances(startDate, endDate, page, size);
        List<PerformanceInfoResDto> gochukPerformances = getGOCHUKPerformances(startDate, endDate, page, size);

        List<String> allPerformanceIds = Stream.of(kspoPerformances, jangchugPerformances, jamsilPerformances, gochukPerformances)
                .flatMap(List::stream)
                .map(PerformanceInfoResDto::getId)
                .toList();

        List<PerformanceEntity> allPerformances = allPerformanceIds.stream()
                .map(this::getPerformanceDetails)
                .filter(list -> !list.isEmpty())
                .flatMap(List::stream)
                .map(p -> toPerformance(p.getName(), p.getArtist(), p.getStartDate(), p.getEndDate()))
                .toList();

        performanceProvider.saveAll(allPerformances);
    }

    private List<PerformanceInfoResDto> getKSPOPerformances(String startDate, String endDate, int page, int size) {
        return kopisProvider.getPerformanceList(KSPO_DOME.getCode(), GENRE_CODE, startDate, endDate, page, size);
    }

    private List<PerformanceInfoResDto> getJANGCHUGPerformances(String startDate, String endDate, int page, int size) {
        return kopisProvider.getPerformanceList(JANGCHUNG.getCode(), GENRE_CODE, startDate, endDate, page, size);
    }

    private List<PerformanceInfoResDto> getJAMSILPerformances(String startDate, String endDate, int page, int size) {
        return kopisProvider.getPerformanceList(JAMSIL_ARENA.getCode(), GENRE_CODE, startDate, endDate, page, size);
    }

    private List<PerformanceInfoResDto> getGOCHUKPerformances(String startDate, String endDate, int page, int size) {
        return kopisProvider.getPerformanceList(GOCHEOK_SKY_DOME.getCode(), GENRE_CODE, startDate, endDate, page, size);
    }

    public List<PerformanceDetailResDto> getPerformanceDetails(String performanceId) {
        return kopisProvider.getPerformanceDetails(performanceId);
    }
}
