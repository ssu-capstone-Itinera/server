package com.travel.domain.placetype.entity.restaurant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RestaurantPriceRange {
    NONE("선택 안 함"),
    INEXPENSIVE("저가"),
    MODERATE("중급"),
    EXPENSIVE("고급"),
    PREMINUM("최고급");
    private final String value;

}
