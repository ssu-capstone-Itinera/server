package com.travel.domain.itinerary.dto;


import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ItinerarySaveResponse {
    private Long itineraryId;
    private LocalDate tourDate;
    private List<SimplePlaceDto> places;

}
