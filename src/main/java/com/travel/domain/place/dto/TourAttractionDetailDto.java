package com.travel.domain.place.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class TourAttractionDetailDto {
    private String placeId;
    private String name;
    private String address;
    private List<String> photos;  // 사진 목록
    private List<String> openingHours;  // 오픈 시간 목록
    private String website;

    private Double rating;

    private String priceLevel;

    private List<String> reviews;  // 리뷰 목록

}
