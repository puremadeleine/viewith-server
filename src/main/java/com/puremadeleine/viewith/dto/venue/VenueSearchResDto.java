package com.puremadeleine.viewith.dto.venue;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VenueSearchResDto {

    String venueId;
    String venueName;
    String venueLocation;
}
