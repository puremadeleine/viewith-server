package com.puremadeleine.viewith.dto.review.response;

import com.puremadeleine.viewith.domain.review.Block;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewInfoResDto {

    Long reviewId;
    String content;
    Float rating;
    Long createTime;
    List<String> imageList;
    ReviewerInfoResDto userInfo;
    SeatInfoResDto seatInfo;
    SeatBookmarkInfo seatBookmarkInfo;

    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class SeatBookmarkInfo {

        Long seatId;
        String floor;
        String section;
        String seatRow;
        String seatColumn;
        Block block;
        Boolean bookmarked;
    }
}
