package com.travel.domain.placetype.entity.lodge;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LocationFeature {
    NONE("선택 안 함"),
    DOWNTOWN("도심"),
    BEACH("바다 근처"),
    NEARSTATION("역 근처"),
    NEARATTRACTIONS("관광지 인근"),
    NATURE("자연 속");

    private final String value;
}
