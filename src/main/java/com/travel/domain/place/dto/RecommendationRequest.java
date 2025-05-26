package com.travel.domain.place.dto;

import java.util.List;

import com.travel.domain.placetype.entity.cafe.CafeTag;
import com.travel.domain.placetype.entity.restaurant.RestaurantType;
import com.travel.domain.placetype.entity.tourattraction.SubjectiveTag;
import com.travel.domain.placetype.entity.tourattraction.TourattractionTag;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecommendationRequest {
    /*
    뭐가 필요할까
    날짜... 지역 키워드, 테그 명칭들(관광지, 카페, )
     */
    private String mainTourPlace;

    private int radius;
    private List<TourattractionTag> tourattractionTagList;
    private List<SubjectiveTag> subjectiveTagList;
    private List<RestaurantType> restaurantTypeList;
    private List<CafeTag> cafeTagList;
}
