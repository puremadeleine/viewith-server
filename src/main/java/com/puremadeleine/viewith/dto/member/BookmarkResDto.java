package com.puremadeleine.viewith.dto.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Value
@Jacksonized
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookmarkResDto {
    List<BookmarkDto> bookmarks;

    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class BookmarkDto {
        long venueId;
        String venueName;
        List<BookmarkFloorDto> bookmarkFloors;
    }

    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class BookmarkFloorDto {
        String bookmarkFloor;
        List<BookmarkSeatDto> bookmarkSeats;
    }

    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class BookmarkSeatDto {
        long bookmarkId;
        String bookmarkSection;
        Integer bookmarkRow;
        LocalDateTime lastUpdateDate;
    }
}
