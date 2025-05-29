package com.travel.domain.placetype.entity.cafe;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CafeTag {
    ALLOWS_DOGS("cafe allowsDogs", "restaurant", "allowsDogs"),
    CURBSIDE_PICKUP( "cafe curbsidePickup", "restaurant", "curbsidePickup"),
    DINE_IN("cafe dineIn", "restaurant", "dineIn"),
    GOOD_FOR_CHILDREN( "cafe goodForChildren", "restaurant" , "goodForChildren"),
    GOOD_FOR_GROUPS( "cafe goodForGroups", "restaurant", "goodForGroups"),
    MENU_FOR_CHILDREN( "cafe menuForChildren", "restaurant", "menuForChildren"),
    PARKING_OPTIONS( "cafe parkingOptions", "restaurant" ,"parkingOptions"),
    RESERVABLE( "cafe reservable", "restaurant", "reservable"),

    SERVES_BEER("cafe servesBeer", "restaurant", "servesBeer"),
    SERVES_COCKTAILS( "cafe servesCocktails", "restaurant", "servesCocktails"),
    SERVES_WINE( "cafe servesWine", "restaurant", "servesWine"),

    SERVES_BREAKFAST( "cafe servesBreakfast", "restaurant", "servesBreakfast"),
    SERVES_LUNCH("cafe servesLunch", "restaurant", "servesLunch"),
    SERVES_DINNER("cafe servesDinner", "restaurant", "servesDinner"),
    SERVES_BRUNCH("cafe servesBrunch", "restaurant", "servesBrunch"),

    SERVES_DESSERT( "cafe servesDessert", "restaurant", "servesDessert"),
    SERVES_VEGETARIAN_FOOD("cafe servesVegetarianFood", "restaurant", "servesVegetarianFood");
    OUTDOORSEATING("cafe outdoorSeating", "restaurant", "outdoorSeating"),
    TAKEOUT("cafe takeout","restaurant","takeout"),


    private final String keyword;
    private final String type;
    private final String value;
}
