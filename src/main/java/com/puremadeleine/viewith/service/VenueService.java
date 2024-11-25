package com.puremadeleine.viewith.service;

import com.puremadeleine.viewith.domain.venue.PerformanceEntity;
import com.puremadeleine.viewith.dto.venue.VenueListResDto;
import com.puremadeleine.viewith.provider.PerformanceProvider;
import com.puremadeleine.viewith.provider.VenueProvider;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VenueService {

    private static final int PERFORMANCE_MAX_DISPLAY = 4;

    VenueProvider venueProvider;
    PerformanceProvider performanceProvider;

    public VenueListResDto getVenues() {
        return makeMock();

//        List<VenueEntity> venues = venueProvider.getVenues();
//        var performancesPerVenue = getTopPerformancesPerVenue(PERFORMANCE_MAX_DISPLAY);
//
//        //TODO: 해당 작업 mapper or converter 로 이동
//        var venueRes = venues.stream()
//                .map(venue -> {
//                    Long venueId = venue.getId();
//                    return VenueListResDto.VenueResDto.builder()
//                            .venueId(venueId)
//                            .venueName(venue.getName())
//                            .venueLocation(venue.getLocation())
//                            .performances(performancesPerVenue.getOrDefault(venueId, List.of()))
//                            .build();
//                })
//                .toList();
//
//
//        return VenueListResDto.builder()
//                .venues(venueRes)
//                .build();
    }

    public Map<Long, List<VenueListResDto.Performance>> getTopPerformancesPerVenue(int maxPerformances) {
        List<PerformanceEntity> performances = performanceProvider.findTopPerformancesPerVenue(maxPerformances);

        return performances.stream()
                .collect(Collectors.groupingBy(
                        performance -> performance.getVenue().getId(),
                        Collectors.mapping(
                                // TODO: 해당 작업 mapper, converter 로 이동
                                (p) -> VenueListResDto.Performance.builder()
                                        .artist(p.getArtist())
                                        .imageUrl(p.getImageUrl())
                                        .build(),
                                Collectors.toList()
                        )
                ));
    }

    private VenueListResDto makeMock() {
        // Create performances for Venue 1
        VenueListResDto.Performance performance1 = VenueListResDto.Performance.builder()
                .artist("BTS")
                .imageUrl("http://example.com/bts.jpg")
                .build();

        VenueListResDto.Performance performance2 = VenueListResDto.Performance.builder()
                .artist("Blackpink")
                .imageUrl("http://example.com/blackpink.jpg")
                .build();

        // Create performances for Venue 2
        VenueListResDto.Performance performance3 = VenueListResDto.Performance.builder()
                .artist("IU")
                .imageUrl("http://example.com/iu.jpg")
                .build();

        VenueListResDto.Performance performance4 = VenueListResDto.Performance.builder()
                .artist("NCT 127")
                .imageUrl("http://example.com/nct127.jpg")
                .build();

        VenueListResDto.Performance performance5 = VenueListResDto.Performance.builder()
                .artist("NCT DREAM")
                .imageUrl("http://example.com/nctdream.jpg")
                .build();

        // Create Venue 1
        VenueListResDto.VenueResDto venue1 = VenueListResDto.VenueResDto.builder()
                .venueId(1L)
                .venueName("고척 스카이돔")
                .venueLocation("서울특별시 구로구 경인로 ")
                .performances(List.of(performance1, performance2, performance4, performance5))
                .build();

        // Create Venue 2
        VenueListResDto.VenueResDto venue2 = VenueListResDto.VenueResDto.builder()
                .venueId(2L)
                .venueName("KSPO DOME")
                .venueLocation("서울특별시 구로구 경인로 430")
                .performances(List.of(performance3))
                .build();

        return VenueListResDto.builder()
                .venues(List.of(venue1, venue2))
                .build();
    }
}
