package com.puremadeleine.viewith.converter.performance;

import com.puremadeleine.viewith.domain.venue.PerformanceEntity;

public class UpcomingPerformanceServiceConverter {

    public static PerformanceEntity toPerformance(String title, String artist, String startDate, String endDate) {
        return PerformanceEntity.builder()
                .title(title)
                .artist(artist)
                .build();
    }
}
