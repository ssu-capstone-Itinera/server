package com.travel.domain.datapipeline.google.dto.request;


import com.travel.domain.placetype.entity.cafe.CafeTag;
import com.travel.domain.placetype.entity.restaurant.RestaurantType;
import com.travel.domain.placetype.entity.tourattraction.TourattractionTag;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GoogleRequest {
    private double lat;
    private double lng;
    private TourattractionTag tourattractionTag;
    private CafeTag cafeTag;
    private RestaurantType restaurantType;

    private String placeType;
    private int maxResults;
}