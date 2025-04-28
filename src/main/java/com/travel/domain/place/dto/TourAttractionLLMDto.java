package com.travel.domain.place.dto;

import com.travel.domain.placetype.entity.tourattraction.SubjectiveTag;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
public class TourAttractionLLMDto {
    private List<TourAttractionDetailDto> tourAttractionDetailDtoList;
    private List<SubjectiveTag> subjectiveTags;
}
