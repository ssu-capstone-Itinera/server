package com.travel.domain.place.api;

import java.util.ArrayList;
import java.util.List;

import com.travel.domain.place.dto.TourAttractionLLMDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travel.domain.place.dto.TourAttractionDetailDto;
import com.travel.domain.place.dto.TourAttractionListDto;
import com.travel.domain.place.dto.request.GoogleRequest;
import com.travel.domain.place.service.PlaceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/place")
@Tag(name = "Place", description = "장소 추천 API")
public class PlaceController {
    private final PlaceService placeService;

    @Operation(summary = "장소 조회 (tourAttraction test) - 사용 api 아님")
    @PostMapping("/search")
    public ResponseEntity<TourAttractionListDto> searchAttractions(
            @RequestBody GoogleRequest googleRequest) {
        TourAttractionListDto response = new TourAttractionListDto();
        if (googleRequest.getPlaceType().equals("tourist_attraction")) {
            response = placeService.searchPlace(googleRequest);
        }
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "장소 조회 디테일(tourAttraction test) - 사용 api 아님")
    @PostMapping("/search/detail")
    public ResponseEntity<List<TourAttractionDetailDto>> searchDetailAttraction(
            @RequestBody GoogleRequest googleRequest) {
        List<TourAttractionDetailDto> response = new ArrayList<>();
        if (googleRequest.getPlaceType().equals("tourist_attraction")) {
            response = placeService.searchPlaceDetail(googleRequest);
        }
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "장소 조회 후 태깅 (tourAttraction test) - 사용 api 아님")
    @PostMapping("/llm/tagging")
    public ResponseEntity<List<TourAttractionLLMDto>> getLLMTagging(
            @RequestBody GoogleRequest googleRequest) {
        List<TourAttractionLLMDto> response = placeService.saveTourAttraction(googleRequest);
        return ResponseEntity.ok(response);
    }

}
