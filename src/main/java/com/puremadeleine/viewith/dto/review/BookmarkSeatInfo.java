package com.puremadeleine.viewith.dto.review;

import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookmarkSeatInfo {
    Long venueId;
    String floor;
    String section;
    String row;
    @Nullable
    LocalDateTime lastCreatedAt;
}
