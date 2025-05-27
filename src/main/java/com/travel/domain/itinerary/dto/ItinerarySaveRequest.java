package com.travel.domain.itinerary.dto;


import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
public class ItinerarySaveRequest {
    private LocalDate tourDate;

    private Long tripId;
    private Long memberId;

    private List<SimplePlaceDto> places;
}
