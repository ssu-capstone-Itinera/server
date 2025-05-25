package com.travel.domain.place.dto;

import com.travel.domain.categories.entity.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaceResponse {
    private Long placeId;
    private String placeGoogleId;
    private String name;
    private String address;
    private Double rating;
    private Category category;
    private Double lng;
    private Double lat;
}
