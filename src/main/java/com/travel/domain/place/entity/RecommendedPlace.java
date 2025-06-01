package com.travel.domain.place.entity;

import com.travel.domain.categories.entity.Category;
import com.travel.domain.place.dto.PlaceDetailResponse;
import com.travel.domain.placetype.entity.cafe.CafeTag;
import com.travel.domain.placetype.entity.restaurant.RestaurantType;
import com.travel.domain.placetype.entity.tourattraction.SubjectiveTag;
import com.travel.domain.placetype.entity.tourattraction.TourattractionTag;
import com.travel.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "recommended_place")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendedPlace extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String placeGoogleId;

    private String name;

    private String address;

    @Enumerated(EnumType.STRING)
    private Category category;

    private Double rating;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "recommended_place_cafe_tags", joinColumns = @JoinColumn(name = "recommended_place_id"))
    @Enumerated(EnumType.STRING)
    private List<CafeTag> cafeTags;

    @Enumerated(EnumType.STRING)
    private RestaurantType restaurantType;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "recommended_place_tour_tags", joinColumns = @JoinColumn(name = "recommended_place_id"))
    @Enumerated(EnumType.STRING)
    private List<TourattractionTag> tourattractionTags;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "recommended_place_subjective_tags", joinColumns = @JoinColumn(name = "recommended_place_id"))
    @Enumerated(EnumType.STRING)
    private List<SubjectiveTag> subjectiveTags;

    private LocalDateTime recommendedAt;


    public static RecommendedPlace from(PlaceDetailResponse detail) {
        return RecommendedPlace.builder()
                .placeGoogleId(detail.getPlaceGoogleId())
                .name(detail.getName())
                .address(detail.getAddress())
                .category(detail.getCategory())
                .rating(detail.getRating())
                .cafeTags(detail.getCafeTags())
                .restaurantType(detail.getRestaurantType())
                .tourattractionTags(detail.getTourattractionTags())
                .subjectiveTags(detail.getSubjectiveTags())
                .recommendedAt(LocalDateTime.now())
                .build();
    }
}
