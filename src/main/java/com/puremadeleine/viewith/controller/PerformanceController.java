package com.puremadeleine.viewith.controller;

import com.puremadeleine.viewith.dto.performance.PerformanceSearchResDto;
import com.puremadeleine.viewith.service.PerformanceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/performances")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PerformanceController {

    PerformanceService performanceService;

    @GetMapping("/search")
    public List<PerformanceSearchResDto> searchPerformance(@RequestParam String keyword) {
        return performanceService.searchPerformance(keyword);
    }
}
