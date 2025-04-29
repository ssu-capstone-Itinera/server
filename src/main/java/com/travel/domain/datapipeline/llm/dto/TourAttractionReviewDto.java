package com.travel.domain.datapipeline.llm.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TourAttractionReviewDto {
    private String name;
    private String placeId;


    private List<String> reviews;
}
