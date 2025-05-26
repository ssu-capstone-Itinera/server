package com.travel.domain.categories.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    TOURATTRACTION("tourist_attraction"),
    RESTAURANT("restaurant"),
    CAFE("cafe"),
    MY_PLACE("my_place"),
    OTHER("Other");
    private final String value;

    public String getValue() {
        return value;
    }
}
