package com.travel.domain.itinerary.dto;

import com.travel.domain.categories.entity.Category;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class SimplePlaceDto {
    private String address;
    private Category category;
    private Double lat;
    private Double lng;
    private String name;
    private String placeGoogleId;
    private Integer placeId;
    private Double rating;
}
