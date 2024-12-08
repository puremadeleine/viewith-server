package com.puremadeleine.viewith.controller;

import com.puremadeleine.viewith.dto.venue.VenueListResDto;
import com.puremadeleine.viewith.dto.venue.VenueResDto;
import com.puremadeleine.viewith.service.VenueService;
import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{venue_id}/seats")
    public Object getVenueSeats(@PathVariable(value = "venue_id") long venueId,
                                @Nullable @RequestParam(required = false) String floor) {
        return null;
    }
}
