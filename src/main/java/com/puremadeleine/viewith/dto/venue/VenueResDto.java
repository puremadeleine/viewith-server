package com.puremadeleine.viewith.dto.venue;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VenueResDto {
    String venueUrl;
    List<String> sections;
    List<Stage> stages;
    List<VenueReviewInfo> venueReviewInfos;

    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Stage {
        long stageId;
        VenueStageType type;
        String name;
        String svgUrl;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class VenueReviewInfo {
        String sectionKey;
        long reviewCnt;
    }
}
