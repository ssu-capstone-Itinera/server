package com.travel.domain.place.api;

import com.travel.domain.datapipeline.google.dto.request.GoogleRequest;
import com.travel.domain.place.dto.PlaceDetailResponse;
import com.travel.domain.place.dto.PlaceResponse;
import com.travel.domain.place.dto.RecommendationRequest;
import com.travel.domain.place.service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/place")
@Tag(name = "Place", description = "여행지 추천 api")
public class PlaceController {
    private final PlaceService placeService;

    @Operation(summary = "장소 추천 api")
    @PostMapping
    public ResponseEntity<PlaceResponse> searchRecommendation(
            @RequestBody RecommendationRequest recommendationRequest) {

        return ResponseEntity.ok(placeService.searchRecommendation(recommendationRequest));
    }

    @Operation(summary = "장소 정보 상세 조회")
    @GetMapping("{placeGoogleId}")
    public ResponseEntity<PlaceDetailResponse> getPlaceDetail(@RequestParam String placeGoogleId){

        return ResponseEntity.ok(placeService.getPlaceDetail(placeGoogleId));
    }

}
