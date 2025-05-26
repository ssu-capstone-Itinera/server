package com.travel.domain.place.dto;

import java.util.List;

import com.travel.domain.categories.entity.Category;
import com.travel.domain.placetype.entity.cafe.CafeTag;
import com.travel.domain.placetype.entity.restaurant.RestaurantType;
import com.travel.domain.placetype.entity.tourattraction.SubjectiveTag;
import com.travel.domain.placetype.entity.tourattraction.TourattractionTag;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDetailResponse {
    private Long id;
    private Category category;
    private String placeGoogleId;
    private String name;
    private String address;

    private double lat;
    private double lng;
    private String location;
    private Double rating;
    private String phoneNumber;
    private String webSite;
    private List<String> openingHours;
    private String priceLevel;
    private String description;

    private List<String> reviews;

    private List<CafeTag> cafeTags;

    private RestaurantType restaurantType;

    private List<TourattractionTag> tourattractionTags;

    private List<SubjectiveTag> subjectiveTags;
}
