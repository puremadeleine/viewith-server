package com.puremadeleine.viewith.dto.review;

import com.puremadeleine.viewith.dto.client.UserInfoResDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewInfoResDto {

    Long reviewId;
    String content;
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
