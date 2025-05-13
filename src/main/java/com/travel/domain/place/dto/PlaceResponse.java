package com.travel.domain.place.dto;

import com.travel.domain.categories.entity.Category;
import com.travel.domain.placetype.dto.response.CafeResponse;
import com.travel.domain.placetype.dto.response.RestaurantResponse;
import com.travel.domain.placetype.dto.response.TourattractionResponse;
import com.travel.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Builder
public class PlaceResponse {
        private List<CafeResponse> cafeResponseList;
        private List<RestaurantResponse> restaurantResponseList;
        private List<TourattractionResponse> tourattractionResponseList;


}
