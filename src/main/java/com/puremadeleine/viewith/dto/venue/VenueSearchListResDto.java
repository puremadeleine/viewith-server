package com.puremadeleine.viewith.dto.venue;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VenueSearchListResDto {

    List<VenueSearchResDto> venues;

    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class VenueSearchResDto {

        Long venueId;
        String venueName;
        String venueLocation;
    }
}
