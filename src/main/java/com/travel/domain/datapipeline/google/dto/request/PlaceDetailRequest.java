package com.travel.domain.datapipeline.google.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlaceDetailRequest {
    private List<String> placeIds;
}
 