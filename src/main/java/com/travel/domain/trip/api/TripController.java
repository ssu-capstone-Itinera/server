package com.travel.domain.trip.api;

import com.travel.domain.itinerary.dao.ItineraryRepository;
import com.travel.domain.itinerary.dto.SimplePlaceDto;
import com.travel.domain.itinerary.service.ItineraryService;
import com.travel.domain.trip.dto.request.TripSaveRequest;
import com.travel.domain.trip.dto.response.TripGetResponse;
import com.travel.domain.trip.dto.response.TripSaveResponse;
import com.travel.domain.trip.entity.Trip;
import com.travel.domain.trip.service.TripService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trip")
@RequiredArgsConstructor
@Tag(name = "Trip", description = "TripController입니다.")
public class TripController {

    private final TripService tripService;
    private final ItineraryService itineraryService;

    @PostMapping("/save")
    public ResponseEntity<TripSaveResponse> saveTrip(@RequestBody TripSaveRequest request) {
        return ResponseEntity.ok(tripService.saveTrip(request));
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<TripGetResponse> getTrip(
            @PathVariable Long tripId,
            @RequestParam Long memberId) {
        return ResponseEntity.ok(tripService.getTripGetResponse(tripId, memberId));
    }

}