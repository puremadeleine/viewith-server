package com.puremadeleine.viewith.converter.performance;

import com.puremadeleine.viewith.domain.venue.PerformanceEntity;
import com.puremadeleine.viewith.domain.venue.VenueEntity;
import com.puremadeleine.viewith.dto.client.kopis.PerformanceDetailResDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UpcomingPerformanceServiceConverter {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    public static PerformanceEntity toPerformance(PerformanceDetailResDto performance, VenueEntity venue) {

        return PerformanceEntity.builder()
                .title(performance.getTitle())
                .artist(performance.getArtist())
                .imageUrl(performance.getImgUrl())
                .startDate(LocalDate.parse(performance.getStartDate(), formatter))
                .startDate(LocalDate.parse(performance.getEndDate(), formatter))
                .venue(venue)
                .build();
    }
}
