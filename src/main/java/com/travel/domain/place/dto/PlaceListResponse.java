package com.travel.domain.place.dto;

import com.travel.domain.placetype.dto.response.CafeResponse;
import com.travel.domain.placetype.dto.response.RestaurantResponse;
import com.travel.domain.placetype.dto.response.TourattractionResponse;
import lombok.*;

import java.util.List;

@Getter
@Builder
public class PlaceListResponse {
        private List<PlaceResponse> placeResponseList;

}
