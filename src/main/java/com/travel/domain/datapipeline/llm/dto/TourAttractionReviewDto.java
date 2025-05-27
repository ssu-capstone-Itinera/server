package com.travel.domain.datapipeline.llm.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TourAttractionReviewDto {
    private String name;
    private String placeId;

    private List<String> reviews;
}
