package com.puremadeleine.viewith.domain.venue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VenueCode {
    
    KSPO_DOME("FC001247", "올림픽공원"),
    JANGCHUNG("FC001823", "장충체육관"),
    JAMSIL_ARENA("FC001837", "잠실종합운동장"),
    GOCHEOK_SKY_DOME("FC001901", "고척스카이돔");

    private final String code;
    private final String name;
}
