package com.travel.domain.datapipeline.google.dto;

import java.util.List;

import com.travel.domain.placetype.entity.tourattraction.SubjectiveTag;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TourAttractionLLMDto {
    private List<PlaceDetailDto> placeDetailDtoList;
    private List<SubjectiveTag> subjectiveTags;
}
