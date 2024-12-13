package com.puremadeleine.viewith.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCntDto {
    Long reviewCount;
    String floor;
    String section;
}
