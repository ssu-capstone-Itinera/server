package com.travel.domain.place.dto;

import com.travel.domain.place.entity.Place;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MyPlaceResponse {
    private List<Place> myPlaceSuggest;
}