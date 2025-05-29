package com.travel.domain.placetype.entity.cafe;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CafeTag {
    ALLOWS_DOGS("allowsDogs"),
    CURBSIDE_PICKUP("curbsidePickup"),
    DINE_IN("dineIn"),
    GOOD_FOR_CHILDREN("goodForChildren"),
    GOOD_FOR_GROUPS("goodForGroups"),
    MENU_FOR_CHILDREN("menuForChildren"),
    PARKING_OPTIONS("parkingOptions"),
    RESERVABLE("reservable"),
    OUTDOORSEATING("outdoorSeating"),
    TAKEOUT("takeout"),


    //주류
    SERVES_BEER("servesBeer"),
    SERVES_COCKTAILS("servesCocktails"),
    SERVES_WINE("servesWine"),

    //식사
    SERVES_BREAKFAST("servesBreakfast"),
    SERVES_LUNCH("servesLunch"),
    SERVES_DINNER("servesDinner"),
    SERVES_BRUNCH("servesBrunch"),


    SERVES_DESSERT("servesDessert"),
    SERVES_VEGETARIAN_FOOD("servesVegetarianFood");

    private final String value;

    public String getValue() {
        return value;
    }

}
