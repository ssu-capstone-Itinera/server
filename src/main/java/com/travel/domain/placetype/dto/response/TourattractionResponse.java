package com.travel.domain.placetype.dto.response;

import com.travel.domain.categories.entity.Category;
import com.travel.domain.place.dto.PlaceResponse;
import com.travel.domain.placetype.entity.tourattraction.ApiTag;
import com.travel.domain.placetype.entity.tourattraction.SubjectiveTag;


import java.util.List;

public class TourattractionResponse {
    private Long placeId;
    private String googleId;
    private String name;
    private String address;
    private Double rating;

    private List<ApiTag> apiTags;

}
