package com.puremadeleine.viewith.scheduler;

import com.puremadeleine.viewith.service.UpcomingPerformanceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UpcomingPerformanceScheduler {

    UpcomingPerformanceService upcomingPerformanceService;

    static final int SCHEDULE_INTERVAL_DAYS = 3;
    static final int PAGE = 1;
    static final int SIZE = 50;

    public void process() {

        String startDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String formattedDate = LocalDate.now().plusDays(SCHEDULE_INTERVAL_DAYS).format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        upcomingPerformanceService.saveUpcomingPerformances(startDate, formattedDate, SIZE, PAGE);
    }

}
