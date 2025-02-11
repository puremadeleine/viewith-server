package com.puremadeleine.viewith.dto.review.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

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
    Long createTime;
    List<String> imageList;
    ReviewerInfoResDto userInfo;
    SeatInfoResDto seatInfo;
}
