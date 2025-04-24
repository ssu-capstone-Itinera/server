package com.travel.domain.place.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
public class TourAttractionListDto {
    private List<PlaceDto> results;
    private String nextPageToken;
}
