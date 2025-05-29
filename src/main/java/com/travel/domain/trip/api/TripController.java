package com.travel.domain.trip.api;

import com.travel.domain.itinerary.dao.ItineraryRepository;
import com.travel.domain.itinerary.dto.SimplePlaceDto;
import com.travel.domain.itinerary.service.ItineraryService;
import com.travel.domain.trip.dto.request.TripSaveRequest;
import com.travel.domain.trip.dto.response.SimpleTripResponse;
import com.travel.domain.trip.dto.response.TripGetResponse;
import com.travel.domain.trip.dto.response.TripSaveResponse;
import com.travel.domain.trip.entity.Trip;
import com.travel.domain.trip.service.TripService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Trip 저장 API")
    @PostMapping("/save")
    public ResponseEntity<TripSaveResponse> saveTrip(@RequestBody TripSaveRequest request) {
        return ResponseEntity.ok(tripService.saveTrip(request));
    }


    @Operation(summary = "MemberId로 특정 사용자의 전체 TripList를 조회. 간단한 정보만 반환하고 세부정보는 getTrip 사용")
    @GetMapping("/{memberId}")
    public ResponseEntity<List<SimpleTripResponse>> getSimpleTripListByMemberId(@PathVariable Long memberId) {
        return ResponseEntity.ok(tripService.getSimpleTripListByMemberId(memberId));
    }

    @Operation(summary = "TripId와 MemberId로 단일 Trip 정보 get")
    @GetMapping("/{memberId}/{tripId}")
    public ResponseEntity<TripGetResponse> getTrip(
            @PathVariable Long tripId,
            @PathVariable Long memberId) {
        return ResponseEntity.ok(tripService.getTripGetResponse(tripId, memberId));
    }

}