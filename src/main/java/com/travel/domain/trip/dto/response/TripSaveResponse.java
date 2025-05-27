package com.travel.domain.trip.dto.response;

import com.travel.domain.itinerary.dto.SimplePlaceDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class TripSaveResponse {
    private Long tripId;
    private String title;
    private String mainTourPlace;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isPublic;
    private List<List<SimplePlaceDto>> listOfPlaces;
}
