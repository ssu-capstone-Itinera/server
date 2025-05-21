package com.travel.domain.placetype.entity;


import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum ApiTag {
    BEACH("tourist_attraction", "beach", "natural_feature"),
    MOUNTAIN("tourist_attraction", "mountain", "natural_feature"),
    PARK("tourist_attraction", "park", "park"),
    NATIONAL_PARK("tourist_attraction", "national park", "park"),
    LAKE("tourist_attraction", "lake", "natural_feature"),
    WATERFALL("tourist_attraction", "waterfall", "natural_feature"),
    ISLAND("tourist_attraction", "island", "natural_feature"),

    MUSEUM("tourist_attraction", "museum", "museum"),
    ART_MUSEUM("tourist_attraction", "art museum", "art_gallery"),
    CATHEDRAL("tourist_attraction", "cathedral", "church"),
    CHURCH("tourist_attraction", "church", "church"),
    BUDDHIST_TEMPLE("tourist_attraction", "buddhist temple", "place_of_worship"),
    PALACE("tourist_attraction", "palace", "tourist_attraction"),
    CULTURAL_HERITAGE_SITE("tourist_attraction", "cultural heritage site", "tourist_attraction"),
    HISTORIC_DISTRICT("tourist_attraction", "historic district", "tourist_attraction"),

    THEME_PARK("tourist_attraction", "theme park", "amusement_park"),
    AMUSEMENT_PARK("tourist_attraction", "amusement park", "amusement_park"),
    ZOO("tourist_attraction", "zoo", "zoo"),
    AQUARIUM("tourist_attraction", "aquarium", "aquarium"),
    BOTANICAL_GARDEN("tourist_attraction", "botanical garden", "tourist_attraction"),
    CAMPING_SITE("tourist_attraction", "camping site", "campground"),
    TRADITIONAL_MARKET("tourist_attraction", "traditional market", "tourist_attraction"),
    PERFORMANCE_HALL("tourist_attraction", "performance hall", "tourist_attraction"),
    POPULAR_STREET("tourist_attraction", "popular street", "tourist_attraction"),

    SHOPPING_MALL("tourist_attraction", "shopping mall", "shopping_mall"),
    OBSERVATORY("tourist_attraction", "observatory", "tourist_attraction"),
    BRIDGE("tourist_attraction", "bridge", "tourist_attraction"),
    UNIVERSITY_AREA("tourist_attraction", "university area", "tourist_attraction"),
    STADIUM("tourist_attraction", "stadium", "stadium"),
    HARBOR("tourist_attraction", "harbor", "tourist_attraction"),
    LIGHTHOUSE("tourist_attraction", "lighthouse", "tourist_attraction"),

    BIKE_TRAIL("tourist_attraction", "bike trail", "tourist_attraction"),
    GOLF_COURSE("tourist_attraction", "golf course", "golf_course"),
    NIGHT_VIEW_SPOT("tourist_attraction", "night view spot", "tourist_attraction"),
    FAMOUS_PLACE("tourist_attraction", "famous place", "tourist_attraction"),

    // 식당 그룹
    KOREAN_RESTAURANT("restaurant", "korean restaurant", "restaurant"),
    JAPANESE_RESTAURANT("restaurant", "japanese restaurant", "restaurant"),
    CHINESE_RESTAURANT("restaurant", "chinese restaurant", "restaurant"),
    WESTERN_RESTAURANT("restaurant", "western restaurant", "restaurant"),
    ASIAN_RESTAURANT("restaurant", "asian restaurant", "restaurant"),
    FAST_FOOD("restaurant", "fast food", "restaurant"),
    VEGETARIAN("restaurant", "vegetarian", "restaurant"),
    BUFFET("restaurant", "buffet", "restaurant"),
    CAFE("restaurant", "cafe", "restaurant"),

    // 카페 그룹
    ALLOWS_DOGS("cafe", "cafe allowsDogs", "restaurant"),
    CURBSIDE_PICKUP("cafe", "cafe curbsidePickup", "restaurant"),
    DINE_IN("cafe", "cafe dineIn", "restaurant"),
    GOOD_FOR_CHILDREN("cafe", "cafe goodForChildren", "restaurant"),
    GOOD_FOR_GROUPS("cafe", "cafe goodForGroups", "restaurant"),
    MENU_FOR_CHILDREN("cafe", "cafe menuForChildren", "restaurant"),
    PARKING_OPTIONS("cafe", "cafe parkingOptions", "restaurant"),
    RESERVABLE("cafe", "cafe reservable", "restaurant"),

    SERVES_BEER("cafe", "cafe servesBeer", "restaurant"),
    SERVES_COCKTAILS("cafe", "cafe servesCocktails", "restaurant"),
    SERVES_WINE("cafe", "cafe servesWine", "restaurant"),

    SERVES_BREAKFAST("cafe", "cafe servesBreakfast", "restaurant"),
    SERVES_LUNCH("cafe", "cafe servesLunch", "restaurant"),
    SERVES_DINNER("cafe", "cafe servesDinner", "restaurant"),
    SERVES_BRUNCH("cafe", "cafe servesBrunch", "restaurant"),

    SERVES_DESSERT("cafe", "cafe servesDessert", "restaurant"),
    SERVES_VEGETARIAN_FOOD("cafe", "cafe servesVegetarianFood", "restaurant");

    private final String group;
    private final String keyword;
    private final String type;

    public String getGroup() {
        return group;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getType() {
        return type;
    }

    public static String getType(String group, String keyword) {
        return Arrays.stream(values())
                .filter(e -> e.group.equalsIgnoreCase(group)
                        && e.keyword.equalsIgnoreCase(keyword))
                .map(ApiTag::getType)
                .findFirst()
                .orElse(null);
    }

}
