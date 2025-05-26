package com.travel.domain.datapipeline.google.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceDetailRequest {
    private List<String> placeIds;
}
