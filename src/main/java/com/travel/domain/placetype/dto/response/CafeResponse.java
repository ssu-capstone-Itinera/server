package com.travel.domain.placetype.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CafeResponse {
    private Long placeId;
    private String googleId;
    private String name;
    private String address;
    private Double rating;
}
