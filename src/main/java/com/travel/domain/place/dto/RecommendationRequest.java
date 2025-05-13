package com.travel.domain.place.dto;

import com.travel.domain.placetype.entity.cafe.CafeTag;
import com.travel.domain.placetype.entity.restaurant.RestaurantPriceRange;
import com.travel.domain.placetype.entity.restaurant.RestaurantType;
import com.travel.domain.placetype.entity.tourattraction.ApiTag;
import com.travel.domain.placetype.entity.tourattraction.SubjectiveTag;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RecommendationRequest {
    /*
    뭐가 필요할까
    날짜... 지역 키워드, 테그 명칭들(관광지, 카페, )
     */

    private List<ApiTag> apiTagList;
    private List<SubjectiveTag> subjectiveTagList;
    private List<RestaurantType> restaurantTypeList;
    private List<RestaurantPriceRange> restaurantPriceRangeList;
    private List<CafeTag> cafeTagList;

}
