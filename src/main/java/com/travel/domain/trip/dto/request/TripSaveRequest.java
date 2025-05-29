package com.travel.domain.trip.dto.request;

import com.travel.domain.itinerary.dto.SimplePlaceDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TripSaveRequest {
    private Long memberId;
    private String title;
    private List<String> mainTourPlaces;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isPublic;
    private List<List<SimplePlaceDto>> listOfPlaces;
}
