package com.travel.domain.placetype.entity.tourattraction;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TourattractionTag {
    BEACH("beach", "natural_feature"),
    MOUNTAIN("mountain", "natural_feature"),
    PARK( "park", "park"),
    NATIONAL_PARK("national park", "park"),
    LAKE( "lake", "natural_feature"),
    WATERFALL("waterfall", "natural_feature"),
    ISLAND("island", "natural_feature"),

    MUSEUM("museum", "museum"),
    ART_MUSEUM( "art museum", "art_gallery"),
    CATHEDRAL( "cathedral", "church"),
    CHURCH("church", "church"),
    BUDDHIST_TEMPLE("buddhist temple", "place_of_worship"),
    PALACE( "palace", "tourist_attraction"),
    CULTURAL_HERITAGE_SITE("cultural heritage site", "tourist_attraction"),
    HISTORIC_DISTRICT( "historic district", "tourist_attraction"),

    THEME_PARK("theme park", "amusement_park"),
    AMUSEMENT_PARK("amusement park", "amusement_park"),
    ZOO("zoo", "zoo"),
    AQUARIUM("aquarium", "aquarium"),
    BOTANICAL_GARDEN("botanical garden", "tourist_attraction"),
    CAMPING_SITE( "camping site", "campground"),
    TRADITIONAL_MARKET("traditional market", "tourist_attraction"),
    PERFORMANCE_HALL("performance hall", "tourist_attraction"),
    POPULAR_STREET("popular street", "tourist_attraction"),

    SHOPPING_MALL("shopping mall", "shopping_mall"),
    OBSERVATORY("observatory", "tourist_attraction"),
    BRIDGE("bridge", "tourist_attraction"),
    UNIVERSITY_AREA( "university area", "tourist_attraction"),
    STADIUM("stadium", "stadium"),
    HARBOR("harbor", "tourist_attraction"),
    LIGHTHOUSE( "lighthouse", "tourist_attraction"),

    BIKE_TRAIL( "bike trail", "tourist_attraction"),
    GOLF_COURSE( "golf course", "golf_course"),
    NIGHT_VIEW_SPOT("night view spot", "tourist_attraction"),
    FAMOUS_PLACE( "famous place", "tourist_attraction");

    private final String keyword;
    private final String type;

}
