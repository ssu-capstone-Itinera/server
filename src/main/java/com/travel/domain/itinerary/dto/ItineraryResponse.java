package com.travel.domain.itinerary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ItineraryResponse {
    private Long itineraryId;
    private LocalDate tourDate;
    private String dailyTourPlace;
    private List<SimplePlaceDto> places;
}

