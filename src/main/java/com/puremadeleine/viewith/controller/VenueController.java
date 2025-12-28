package com.puremadeleine.viewith.controller;

import com.puremadeleine.viewith.dto.venue.VenueFilterResDto;
import com.puremadeleine.viewith.dto.venue.VenueListResDto;
import com.puremadeleine.viewith.dto.venue.VenueResDto;
import com.puremadeleine.viewith.dto.venue.VenueSearchListResDto;
import com.puremadeleine.viewith.dto.venue.VenueSeatResDto;
import com.puremadeleine.viewith.service.VenueService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{venue_id}")
    public VenueResDto getVenue(@PathVariable(value = "venue_id") long venueId) {
        return venueService.getVenue(venueId);
    }

    @GetMapping("/{venue_id}/filter")
    public VenueFilterResDto getVenueFilter(@PathVariable(value = "venue_id") long venueId) {

        return venueService.getVenueFilter(venueId);
    }

    @GetMapping("/{venue_id}/seats")
    public VenueSeatResDto getVenueSeatInfo(@PathVariable(value = "venue_id") long venueId) {
        return venueService.getVenueSeatInfo(venueId);
    }

    @GetMapping("/search")
    public VenueSearchListResDto searchVenue(@RequestParam String keyword) {
        return venueService.searchVenue(keyword);
    }
}
