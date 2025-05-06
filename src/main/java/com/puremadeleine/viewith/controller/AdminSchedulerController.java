package com.puremadeleine.viewith.controller;

import com.puremadeleine.viewith.domain.venue.PerformanceEntity;
import com.puremadeleine.viewith.dto.performance.request.UpcomingPerformanceReqDto;
import com.puremadeleine.viewith.service.UpcomingPerformanceService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/schedulers")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AdminSchedulerController {

    UpcomingPerformanceService upcomingPerformanceService;

    static final int PAGE = 1;
    static final int SIZE = 50;

    @PostMapping("/upcoming-performances")
    public void processScheduler(@Valid @RequestBody UpcomingPerformanceReqDto req) {

        List<PerformanceEntity> result = new ArrayList<>();
        try {
            result = upcomingPerformanceService.saveUpcomingPerformances(req.startDate(), req.endDate(), PAGE, SIZE);
        } catch (Exception e) {
            log.error("[UpcomingPerformanceScheduler] job failed", e);
        }

        log.info("[UpcomingPerformanceScheduler] fetch upcoming performances job end. {} items have been saved.",
                result.size());
    }
}
