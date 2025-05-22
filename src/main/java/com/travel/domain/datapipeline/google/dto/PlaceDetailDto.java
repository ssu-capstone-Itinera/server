package com.travel.domain.datapipeline.google.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PlaceDetailDto {
    private String placeId;
    private String name;
    private String address;
    private String phoneNumber;
    private String website;
    private Double rating;
    private Double lat;
    private Double lng;
    private List<String> openingHours;
    private String priceLevel;
    private List<String> photos;
    private List<String> reviews;
    private List<String> types;

}
