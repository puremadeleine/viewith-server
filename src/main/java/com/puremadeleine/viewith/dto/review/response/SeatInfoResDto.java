package com.puremadeleine.viewith.dto.review.response;

import com.puremadeleine.viewith.domain.review.Block;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeatInfoResDto {

    Long seatId;
    String floor;
    String section;
    Integer seatRow;
    Integer seatColumn;
    Block block;
}