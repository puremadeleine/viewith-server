package com.puremadeleine.viewith.controller;

import com.puremadeleine.viewith.dto.venue.VenueListResDto;
import com.puremadeleine.viewith.service.VenueService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/venues")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VenueController {

    VenueService venueService;

    @GetMapping()
    public VenueListResDto getVenues(@RequestParam(value = "performance_cnt", required = false, defaultValue = "4") Integer performanceCnt) {
        return venueService.getVenues(performanceCnt);
    }
}
