package com.puremadeleine.viewith.controller;

import com.puremadeleine.viewith.dto.member.MemberInfo;
import com.puremadeleine.viewith.dto.venue.VenueListResDto;
import com.puremadeleine.viewith.dto.venue.VenueResDto;
import com.puremadeleine.viewith.dto.venue.VenueSearchResDto;
import com.puremadeleine.viewith.dto.venue.VenueSeatResDto;
import com.puremadeleine.viewith.service.VenueService;
import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public VenueSeatResDto getVenueSeats(@PathVariable(value = "venue_id") long venueId,
                                         @Nullable @RequestParam(required = false) String floor,
                                         @Nullable @RequestParam(required = false) Long row) {

        return venueService.getVenueSeats(venueId, floor, row);
    }

    @PostMapping("/{venue_id}/seats/{seat_id}/bookmarks")
    public void createBookmark(MemberInfo memberInfo,
                               @PathVariable(value = "venue_id") long venueId,
                               @PathVariable(value = "seat_id") long seatId) {
        venueService.createBookmark(memberInfo, seatId);
    }

    @DeleteMapping("/{venue_id}/seats/{seat_id}/bookmarks")
    public void deleteBookmark(MemberInfo memberInfo,
                               @PathVariable(value = "venue_id") long venueId,
                               @PathVariable(value = "seat_id") long seatId) {
        venueService.deleteBookmark(memberInfo, seatId);
    }

    @DeleteMapping("/{venue_id}/seats/bookmarks")
    public void deleteBookmarks(MemberInfo memberInfo,
                               @PathVariable(value = "venue_id") long venueId,
                               @RequestParam(value = "bookmark_ids") List<Long> bookmarkIds) {
        venueService.deleteBookmarks(memberInfo, bookmarkIds);
    }

    @GetMapping("/search")
    public List<VenueSearchResDto> searchVenue(@RequestParam String keyword, MemberInfo memberInfo) {
        return venueService.searchVenue(keyword);
    }
}
