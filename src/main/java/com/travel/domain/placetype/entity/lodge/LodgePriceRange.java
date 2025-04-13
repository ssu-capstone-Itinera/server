package com.travel.domain.placetype.entity.lodge;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LodgePriceRange {
    NONE("선택 안 함"),
    BUDGET("저가"),
    COMFORTABLE("중급"),
    LUXURY("고급");
    private final String value;
}
