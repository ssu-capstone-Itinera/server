package com.travel.domain.datapipeline.google.dto.response;

import com.travel.domain.place.entity.Place;
import com.travel.domain.place.entity.PlaceDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SaveTourAttractionDto {
    private Place place;
    private PlaceDocument placeDocument;

}
