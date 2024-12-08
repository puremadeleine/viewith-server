package com.puremadeleine.viewith.service;

import com.puremadeleine.viewith.domain.venue.PerformanceEntity;
import com.puremadeleine.viewith.domain.venue.SeatEntity;
import com.puremadeleine.viewith.domain.venue.VenueEntity;
import com.puremadeleine.viewith.domain.venue.VenueStageEntity;
import com.puremadeleine.viewith.dto.review.ReviewCntDto;
import com.puremadeleine.viewith.dto.venue.VenueListResDto;
import com.puremadeleine.viewith.dto.venue.VenueResDto;
import com.puremadeleine.viewith.provider.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
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

    private static final String FLOOR = "FLOOR";
    private static final String SEAT = "SEAT";
    private static final String SEPARATOR = "_";

    VenueProvider venueProvider;
    VenueStageProvider venueStageProvider;
    SeatProvider seatProvider;
    PerformanceProvider performanceProvider;
    ReviewProvider reviewProvider;
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

    public VenueResDto getVenue(long venueId) {
        // 공연장, 무대 정보
        VenueEntity venueEntity = venueProvider.getVenue(venueId);
        List<VenueStageEntity> stageEntities = venueStageProvider.getVenueStages(venueId);
        var stages = venueServiceMapper.toStages(stageEntities);

        // 좌석 정보
        List<SeatEntity> seatEntities = seatProvider.getSeats(venueId);
        var sections = seatEntities.stream()
                .map(SeatEntity::getSection)
                .distinct()
                .toList();
        
        // 리뷰 정보
        Map<String, Long> cntByKey = getReviewCntBySectionKey(venueId);
        var reviewInfos = seatEntities.stream()
                .map(s -> makeSectionKey(s.getFloor(), s.getSection()))
                .map(key -> VenueResDto.VenueReviewInfo.builder()
                        .sectionKey(key)
                        .reviewCnt(cntByKey.getOrDefault(key, 0L))
                        .build()
                )
                .toList();

        return venueServiceMapper.toVenueResDto(venueEntity, sections, stages, reviewInfos);
    }

    private Map<String, Long> getReviewCntBySectionKey(long venueId) {
        List<ReviewCntDto> reviewCountDtos = reviewProvider.countNormalReviewsByVenueAndSeat(venueId);
        return reviewCountDtos.stream()
                .collect(Collectors.toMap(
                        dto -> makeSectionKey(dto.getFloor(), dto.getSection()),
                        ReviewCntDto::getReviewCount
                ));
    }

    private String makeSectionKey(String floor, String section) {
        String prefix = FLOOR.equalsIgnoreCase(floor) ? FLOOR : SEAT;
        return StringUtils.join(prefix, SEPARATOR, section);
    }

    @Mapper(componentModel = "spring")
    public interface VenueServiceMapper {
        @Mapping(source = "venue.id", target = "venueId")
        @Mapping(source = "venue.name", target = "venueName")
        @Mapping(source = "venue.location", target = "venueLocation")
        VenueListResDto.VenueResDto toVenueResDto(VenueEntity venue, List<VenueListResDto.Performance> performances);

        VenueListResDto.Performance toPerformance(PerformanceEntity performance);

        @Mapping(source = "id", target = "stageId")
        VenueResDto.Stage toStage(VenueStageEntity stageEntity);

        List<VenueResDto.Stage> toStages(List<VenueStageEntity> stageEntities);

        @Mapping(source = "venueEntity.imageUrl", target = "venueUrl")
        VenueResDto toVenueResDto(VenueEntity venueEntity,
                                  List<String> sections,
                                  List<VenueResDto.Stage> stages,
                                  List<VenueResDto.VenueReviewInfo> venueReviewInfos);
    }
}
