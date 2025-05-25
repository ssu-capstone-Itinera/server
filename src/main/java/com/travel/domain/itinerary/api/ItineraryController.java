package com.travel.domain.itinerary.api;

import com.travel.domain.itinerary.dto.ItinerarySaveRequest;
import com.travel.domain.itinerary.dto.ItinerarySaveResponse;
import com.travel.domain.itinerary.service.ItineraryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/Itinerary")
@Tag(name = "Itinerary", description = "Itinerary Service 테스트용 컨트롤러입니다. 실제 해당 Controller호출은 없을 듯 합니다(TourService통해 Itinerary Service 호출되는 형식)")
public class ItineraryController {
    private final ItineraryService itineraryService;

    @Operation(summary = "Itinerary 저장 API")
    @PostMapping
    public ResponseEntity<ItinerarySaveResponse> saveItinerary(@RequestBody ItinerarySaveRequest itinerarySaveRequest){
        return ResponseEntity.ok(itineraryService.saveItinerary(itinerarySaveRequest));
    }

}
