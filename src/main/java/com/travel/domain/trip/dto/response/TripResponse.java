package com.travel.domain.trip.dto.response;

import java.time.LocalDate;

import com.travel.domain.trip.entity.Trip;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TripResponse {
    private Long tripId;

    private String title;
    private String mainTourPlace;

    private LocalDate startDate;

    private LocalDate endDate;


    public static TripResponse of(Trip trip) {
        return TripResponse.builder()
                .tripId(trip.getId())
                .title(trip.getTitle())
                .startDate(trip.getStartDate())
                .endDate(trip.getEndDate())
                .build();
    }
}
