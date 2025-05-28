package com.travel.domain.placetype.entity.restaurant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RestaurantType {
    KOREAN_RESTAURANT("korean restaurant", "restaurant"),
    JAPANESE_RESTAURANT( "japanese restaurant", "restaurant"),
    CHINESE_RESTAURANT( "chinese restaurant", "restaurant"),
    WESTERN_RESTAURANT("western restaurant", "restaurant"),
    ASIAN_RESTAURANT("asian restaurant", "restaurant"),
    FAST_FOOD("fast food", "restaurant"),
    VEGETARIAN("vegetarian", "restaurant"),
    BUFFET( "buffet", "restaurant"),
    CAFE("cafe", "restaurant");

    private final String keyword;
    private final String type;
}


