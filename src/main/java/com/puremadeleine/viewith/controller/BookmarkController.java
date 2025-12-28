package com.puremadeleine.viewith.controller;

import com.puremadeleine.viewith.dto.member.BookmarkResDto;
import com.puremadeleine.viewith.dto.member.MemberInfo;
import com.puremadeleine.viewith.service.MemberService;
import com.puremadeleine.viewith.service.VenueService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.puremadeleine.viewith.constants.SeatConstants.UNSELECTED_STRING;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookmarkController {

    MemberService memberService;
    VenueService venueService;

    @GetMapping(path = "/members/bookmarks")
    public BookmarkResDto getBookmarks(MemberInfo memberInfo) {
        return memberService.getBookmarks(memberInfo);
    }

    @PostMapping("/venues/{venue_id}/bookmarks")
    public void createBookmark(MemberInfo memberInfo,
                               @PathVariable(value = "venue_id") long venueId,
                               @RequestParam(value = "section") String section,
                               @RequestParam(value = "row", defaultValue = UNSELECTED_STRING) String row) {
        venueService.createBookmark(memberInfo, venueId, section, row);
    }

    @PostMapping("/venues/{venue_id}/seats/{seat_id}/bookmarks")
    public void createBookmark(MemberInfo memberInfo,
                               @PathVariable(value = "venue_id") long venueId,
                               @PathVariable(value = "seat_id") long seatId,
                               @RequestParam(value = "is_section_bookmark", defaultValue = "false") Boolean isSectionBookmark) {
        venueService.createBookmark(memberInfo, seatId, isSectionBookmark);
    }

    @DeleteMapping("/venues/{venue_id}/bookmarks")
    public void deleteBookmark(MemberInfo memberInfo,
                               @PathVariable(value = "venue_id") long venueId,
                               @RequestParam(value = "section") String section,
                               @RequestParam(value = "row", defaultValue = UNSELECTED_STRING) String row) {
        venueService.deleteBookmark(memberInfo, venueId, section, row);
    }

    @DeleteMapping("/venues/{venue_id}/seats/{seat_id}/bookmarks")
    public void deleteBookmark(MemberInfo memberInfo,
                               @PathVariable(value = "venue_id") long venueId,
                               @PathVariable(value = "seat_id") long seatId,
                               @RequestParam(value = "is_section_bookmark", defaultValue = "false") Boolean isSectionBookmark) {
        venueService.deleteBookmark(memberInfo, seatId, isSectionBookmark);
    }

    @DeleteMapping("/venues/{venue_id}/seats/bookmarks")
    public void deleteBookmarks(MemberInfo memberInfo,
                                @PathVariable(value = "venue_id") long venueId,
                                @RequestParam(value = "bookmark_ids") List<Long> bookmarkIds) {
        venueService.deleteBookmarks(memberInfo, bookmarkIds);
    }
}
