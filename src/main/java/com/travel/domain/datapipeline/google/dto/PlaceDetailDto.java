package com.travel.domain.datapipeline.google.dto;


import com.travel.domain.placetype.entity.tourattraction.ApiTag;
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
    private Integer priceLevel;
    private List<String> photos;
    private List<ReviewDto> reviews;
    private List<String> types;

}
