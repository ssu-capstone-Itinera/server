package com.travel.domain.datapipeline.google.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.travel.domain.placetype.entity.ApiTag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class GoogleRequest {

    @Schema(hidden = true)
    @JsonIgnore
    private double lat;

    @Schema(hidden = true)
    @JsonIgnore
    private double lng;

    @Schema(hidden = true)
    @JsonIgnore
    private Integer radius;

    @Schema(description = "main keyword(domain.placetype.entity.ApiTag)", nullable = true)
    private List<ApiTag> keywords;

    @Schema(hidden = true)
    @JsonIgnore
    private ApiTag keyword;

    @Schema(description = "tourist_attraction / restaurant / cafe", nullable = true)
    private String placeType;

    @Schema(description = "priceLevel(restaurant only or null)", nullable = true)
    private Integer priceLevel;

    @Schema(description = "myPlace keyword", nullable = true)
    private String myPlaceQuery;

    @Schema(description = "myPlace address", nullable = true)
    private String myPlaceAddress;

    @Schema(description = "maxResult")
    private int maxResults;
}
