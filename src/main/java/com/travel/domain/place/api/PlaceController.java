package com.travel.domain.place.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travel.domain.place.dto.*;
import com.travel.domain.place.service.PlaceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/places")
@Tag(name = "Place", description = "여행지 추천 api")
public class PlaceController {
    private final PlaceService placeService;

    @Operation(summary = "장소 추천 api")
    @PostMapping
    public ResponseEntity<PlaceListResponse> searchRecommendation(
            @RequestBody RecommendationRequest recommendationRequest) {

        return ResponseEntity.ok(placeService.searchRecommendation(recommendationRequest));
    }

    @Operation(summary = "장소 정보 상세 조회")
    @GetMapping("/{placeGoogleId}")
    public ResponseEntity<PlaceDetailResponse> getPlaceDetail(@PathVariable String placeGoogleId) {

        return ResponseEntity.ok(placeService.getPlaceDetail(placeGoogleId));
    }

    @Operation(summary = "직접 장소 검색")
    @PostMapping("/myPlaceSearch")
    public ResponseEntity<MyPlaceResponse> searchMyPlace(
            @RequestBody MyPlaceRequest myPlaceRequest) {
        return ResponseEntity.ok(placeService.searchMyPlace(myPlaceRequest));
    }

    @Operation(summary = "직접 장소 선택")
    @PostMapping("/myPlaceSelect")
    public ResponseEntity<MyPlaceResponse> selectMyPlace(
            @RequestBody MyPlaceSelectRequest myPlaceSelectRequest) {
        return ResponseEntity.ok(placeService.selectMyPlace(myPlaceSelectRequest));
    }

    @Operation(summary = "인기 장소 조회")
    @PostMapping("/popular")
    public ResponseEntity<PlaceListResponse> getPopularPlaces() {

        return ResponseEntity.ok(placeService.getPopularPlaces());
    }
}
