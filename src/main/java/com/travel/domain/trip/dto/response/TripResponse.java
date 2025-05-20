package com.travel.domain.trip.dto.response;

import com.travel.domain.trip.entity.Trip;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class TripResponse {
    private Long tripId;

    private String title;
    private String regin;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer budget;


    public static TripResponse of(Trip trip) {
        return TripResponse.builder()
                .tripId(trip.getId())
                .title(trip.getTitle())
                .startDate(trip.getStartDate())
                .endDate(trip.getEndDate())
                .build();
    }
}
