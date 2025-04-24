package com.travel.domain.place.dto.request;

import com.travel.domain.placetype.entity.tourattraction.ApiTag;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GoogleRequest {
    private double lat;
    private double lng;
    private ApiTag keyword;
    private String placeType;
    private int maxResults;
}
