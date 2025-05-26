package com.travel.domain.place.dto;

import java.util.List;

import com.travel.domain.place.entity.Place;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MyPlaceResponse {
    private List<Place> myPlaceSuggest;
}
