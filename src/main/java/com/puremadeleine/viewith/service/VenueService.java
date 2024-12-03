package com.puremadeleine.viewith.service;

import com.puremadeleine.viewith.domain.venue.PerformanceEntity;
import com.puremadeleine.viewith.domain.venue.VenueEntity;
import com.puremadeleine.viewith.dto.venue.VenueListResDto;
import com.puremadeleine.viewith.provider.PerformanceProvider;
import com.puremadeleine.viewith.provider.VenueProvider;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VenueService {

    VenueProvider venueProvider;
    PerformanceProvider performanceProvider;
    VenueServiceMapper venueServiceMapper;

    public VenueListResDto getVenues(int performanceCnt) {
        List<VenueEntity> venues = venueProvider.getVenues();
        var performancesPerVenue = getTopPerformancesPerVenue(performanceCnt);
        var venueRes = venues.stream()
                .map(venue -> venueServiceMapper.toVenueResDto(venue, performancesPerVenue.getOrDefault(venue.getId(), List.of())))
                .toList();
        return VenueListResDto.builder()
                .venues(venueRes)
                .build();
    }

    public Map<Long, List<VenueListResDto.Performance>> getTopPerformancesPerVenue(int maxPerformances) {
        List<PerformanceEntity> performances = performanceProvider.findTopPerformancesPerVenue(maxPerformances);

        return performances.stream()
                .collect(Collectors.groupingBy(
                        performance -> performance.getVenue().getId(),
                        Collectors.mapping(
                                venueServiceMapper::toPerformance,
                                Collectors.toList()
                        )
                ));
    }

    @Mapper(componentModel = "spring")
    public interface VenueServiceMapper {
        @Mapping(source = "venue.id", target = "venueId")
        @Mapping(source = "venue.name", target = "venueName")
        @Mapping(source = "venue.location", target = "venueLocation")
        VenueListResDto.VenueResDto toVenueResDto(VenueEntity venue, List<VenueListResDto.Performance> performances);

        VenueListResDto.Performance toPerformance(PerformanceEntity performance);
    }
}
