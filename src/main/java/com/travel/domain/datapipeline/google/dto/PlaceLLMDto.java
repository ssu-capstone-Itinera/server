package com.travel.domain.datapipeline.google.dto;

import com.travel.domain.placetype.entity.tourattraction.SubjectiveTag;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PlaceLLMDto {
    private List<PlaceDetailDto> placeDetailDtoList;
    private List<SubjectiveTag> subjectiveTags;
}
