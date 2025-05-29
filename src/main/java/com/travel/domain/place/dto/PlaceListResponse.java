package com.travel.domain.place.dto;

import java.util.List;

import lombok.*;

@Getter
@Builder
public class PlaceListResponse {
    private List<PlaceResponse> placeResponseList;
}
