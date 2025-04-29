package com.travel.domain.datapipeline.google.dto;

import java.util.List;

import lombok.*;

@Getter
@Setter
public class TourAttractionListDto {
    private List<PlaceDto> results;
    private String nextPageToken;
}
