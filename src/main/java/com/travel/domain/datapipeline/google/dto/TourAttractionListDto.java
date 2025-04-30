package com.travel.domain.datapipeline.google.dto;

import java.util.List;

import com.travel.domain.placetype.entity.tourattraction.ApiTag;
import lombok.*;

@Getter
@Setter
public class TourAttractionListDto {
    private List<PlaceDto> results;
    private String nextPageToken;
}
