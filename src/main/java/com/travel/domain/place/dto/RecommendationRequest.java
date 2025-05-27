package com.travel.domain.place.dto;

import java.util.List;

import com.travel.domain.placetype.entity.cafe.CafeTag;
import com.travel.domain.placetype.entity.restaurant.RestaurantType;
import com.travel.domain.placetype.entity.tourattraction.SubjectiveTag;
import com.travel.domain.placetype.entity.tourattraction.TourattractionTag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecommendationRequest {

    private String mainTourPlace;

    @Schema(hidden = true)
    private int radius;

    private List<TourattractionTag> tourattractionTagList;
    private List<SubjectiveTag> subjectiveTagList;
    private List<RestaurantType> restaurantTypeList;
    private List<CafeTag> cafeTagList;
}
