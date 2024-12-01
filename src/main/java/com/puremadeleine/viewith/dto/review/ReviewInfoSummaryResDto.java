package com.puremadeleine.viewith.dto.review;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(NON_NULL)
public class ReviewInfoSummaryResDto {

    Long reviewId;
    String content;
    String summary;
    Float rating;
    LocalDateTime createTime;
    ReviewerInfoResDto userInfo;

    // todo : seat_bookmark_info 추가

    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class ReviewerInfoResDto {

        Long userId;
        String userNickname;
    }
}
