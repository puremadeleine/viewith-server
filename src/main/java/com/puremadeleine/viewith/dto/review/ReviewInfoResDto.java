package com.puremadeleine.viewith.dto.review;

import com.puremadeleine.viewith.domain.review.Block;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewInfoResDto {

    Long reviewId;
    String content;
    Float rating;
    LocalDateTime createTime;
    List<String> imageList;
    ReviewerInfoResDto userInfo;
    SeatInfoDto seatInfo;

    // todo : seat_bookmark_info 추가

    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class ReviewerInfoResDto {

        Long userId;
        String userNickname;
    }

    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class SeatInfoDto {

        Integer floor;
        String section;
        Integer seatRow;
        Integer seatColumn;
        Block block;
    }
}
