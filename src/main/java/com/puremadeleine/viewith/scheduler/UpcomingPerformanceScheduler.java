package com.puremadeleine.viewith.scheduler;

import com.puremadeleine.viewith.service.UpcomingPerformanceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UpcomingPerformanceScheduler {

    UpcomingPerformanceService upcomingPerformanceService;

    static final int SCHEDULE_INTERVAL_DAYS = 7;
    static final int PAGE = 1;
    static final int SIZE = 50;

//    @Scheduled(cron = "0 0 2 ? * MON")      // 매주 월요일 새벽 두시
    public void process() {

        log.info("[UpcomingPerformanceScheduler] fetch upcoming performances job start");

        String startDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String endDate = LocalDate.now().plusDays(SCHEDULE_INTERVAL_DAYS).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        try {
            upcomingPerformanceService.saveUpcomingPerformances(startDate, endDate, PAGE, SIZE);
        } catch (Exception e) {
            log.error("[UpcomingPerformanceScheduler] job failed", e);
        }

        log.info("[UpcomingPerformanceScheduler] fetch upcoming performances job end");
    }
}