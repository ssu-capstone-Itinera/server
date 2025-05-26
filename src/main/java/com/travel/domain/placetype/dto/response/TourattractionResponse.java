package com.travel.domain.placetype.dto.response;

import java.util.List;

import com.travel.domain.placetype.entity.tourattraction.TourattractionTag;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TourattractionResponse {
    private Long placeId;
    private String googleId;
    private String name;
    private String address;
    private Double rating;

    private List<TourattractionTag> tourattractionTags;
}
