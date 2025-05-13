package com.travel.domain.placetype.dto.response;


import com.travel.domain.place.dto.PlaceResponse;
import com.travel.domain.placetype.entity.restaurant.RestaurantType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RestaurantResponse {
    private Long placeId;
    private String googleId;
    private String name;
    private String address;
    private Double rating;
}
