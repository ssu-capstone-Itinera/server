package com.travel.domain.trip.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class SimpleTripResponse {
    private Long tripId;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;

}
