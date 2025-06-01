package com.travel.domain.place.dto;

import com.travel.domain.categories.entity.Category;
import com.travel.domain.place.dto.PlaceDetailResponse;
import com.travel.domain.placetype.entity.cafe.CafeTag;
import com.travel.domain.placetype.entity.restaurant.RestaurantType;
import com.travel.domain.placetype.entity.tourattraction.SubjectiveTag;
import com.travel.domain.placetype.entity.tourattraction.TourattractionTag;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class RecommendedPlaceResponse {

    private Long id;
    private String placeGoogleId;
    private String name;
    private String address;
    private Category category;
    private LocalDateTime recommendedAt;
    private Double rating;

    private List<CafeTag> cafeTags;
    private RestaurantType restaurantType;
    private List<TourattractionTag> tourattractionTags;
    private List<SubjectiveTag> subjectiveTags;

    public static RecommendedPlaceResponse from(PlaceDetailResponse detail, LocalDateTime recommendedAt) {
        return RecommendedPlaceResponse.builder()
                .id(detail.getId())
                .placeGoogleId(detail.getPlaceGoogleId())
                .name(detail.getName())
                .address(detail.getAddress())
                .category(detail.getCategory())
                .recommendedAt(recommendedAt)
                .rating(detail.getRating())
                .cafeTags(detail.getCafeTags())
                .restaurantType(detail.getRestaurantType())
                .tourattractionTags(detail.getTourattractionTags())
                .subjectiveTags(detail.getSubjectiveTags())
                .build();
    }

}
