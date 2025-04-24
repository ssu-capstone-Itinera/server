package com.travel.domain.place.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceDto {
    private String name;
    private String vicinity;
    private Double lat;
    private Double lng;
    private String icon;
    private Double rating;
    private String placeId;
    private String businessStatus;
    private String photoReference;
    private Boolean openNow;
}
