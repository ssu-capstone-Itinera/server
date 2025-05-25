package com.travel.domain.itinerary.api;

import com.travel.domain.itinerary.dto.ItineraryResponse;
import com.travel.domain.itinerary.dto.ItinerarySaveRequest;
import com.travel.domain.itinerary.dto.ItinerarySaveResponse;
import com.travel.domain.itinerary.service.ItineraryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/Itinerary")
@Tag(name = "Itinerary", description = "Itinerary Service 테스트용 컨트롤러입니다. 실제 해당 Controller 호출은 없을 듯 합니다(TripService 통해 Itinerary Service 호출되는 형식)")
public class ItineraryController {
    private final ItineraryService itineraryService;

    @Operation(summary = "Itinerary 저장 API")
    @PostMapping
    public ResponseEntity<ItinerarySaveResponse> saveItinerary(@RequestBody ItinerarySaveRequest itinerarySaveRequest){
        return ResponseEntity.ok(itineraryService.saveItinerary(itinerarySaveRequest));
    }

    @Operation(summary = " itineraryId로 itinerary검색")
    @GetMapping("/{itineraryid}")
    public ResponseEntity<ItineraryResponse> getItinerary(@PathVariable Long itineraryId){
        return ResponseEntity.ok(itineraryService.getItineraryById(itineraryId));
    }

}
