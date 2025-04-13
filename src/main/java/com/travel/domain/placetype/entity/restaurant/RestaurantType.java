package com.travel.domain.placetype.entity.restaurant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RestaurantType {
    NONE("선택 안 함"),
    KOREAN_RESTAURANT("한식"),
    JAPANESE_RESTAURANT("일식"),
    CHINESE_RESTAURANT("중식"),
    WESTERN_RESTAURANT("양식"),
    ASIAN_RESTAURANT("아시안"),
    FAST_FOOD("패스트푸드"),
    VEGETARIAN("채식"),
    BUFFET("뷔페"),
    CAFE("카페");
    private final String value;
}