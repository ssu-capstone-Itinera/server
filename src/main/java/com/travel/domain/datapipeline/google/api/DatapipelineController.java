package com.travel.domain.datapipeline.google.api;

import java.util.ArrayList;
import java.util.List;

import com.travel.domain.categories.entity.Category;
import com.travel.domain.datapipeline.google.dto.TourAttractionLLMDto;
import com.travel.domain.datapipeline.google.dto.response.SavePlaceDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travel.domain.datapipeline.google.dto.PlaceDetailDto;
import com.travel.domain.datapipeline.google.dto.PlaceListDto;
import com.travel.domain.datapipeline.google.dto.request.GoogleRequest;
import com.travel.domain.datapipeline.google.service.DatapipelineService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/datapipeline")
@Tag(name = "datapipeline", description = "google map api -> llm -> db저장 api (프론트 구현 필요 X) ")
public class DatapipelineController {
    private final DatapipelineService datapipelineService;

    @Operation(summary = "장소 조회 (tourAttraction test) ")
    @PostMapping("/search/tourattraction")
    public ResponseEntity<PlaceListDto> searchAttractions(
            @RequestBody GoogleRequest googleRequest) {
        PlaceListDto response = new PlaceListDto();
        if (googleRequest.equals(Category.TOURATTRACTION)) {
            response = datapipelineService.searchPlace(googleRequest);
        }
        return ResponseEntity.ok(response);
    }

//    @Operation(summary = "장소 조회 (tourAttraction test) ")
//    @PostMapping("/search/cafe")
//    public ResponseEntity<CafeListDto> searchCafes(
//            @RequestBody GoogleRequest googleRequest) {
//        CafeListDto response = new TourAttractionListDto();
//        if (googleRequest.getPlaceType().equals("tourist_attraction")) {
//            response = datapipelineService.searchPlace(googleRequest);
//        }
//        return ResponseEntity.ok(response);
//    }

    @Operation(summary = "장소 조회 디테일(test) ")
    @PostMapping("/search/detail")
    public ResponseEntity<List<PlaceDetailDto>> searchPlaceDetail(
            @RequestBody GoogleRequest googleRequest) {
        List<PlaceDetailDto> response = new ArrayList<>();
        if (googleRequest.equals(Category.TOURATTRACTION)) {
            response = datapipelineService.searchPlaceDetail(googleRequest);
        }
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "장소 조회 후 태깅 (tourAttraction test) ")
    @PostMapping("/llm/tagging")
    public ResponseEntity<List<TourAttractionLLMDto>> getLLMTagging(
            @RequestBody GoogleRequest googleRequest) {
        List<TourAttractionLLMDto> response = datapipelineService.searchTourAttractionWithLLM(googleRequest);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "장소 조회 후 태깅, document 저장 (tourAttraction test) ")
    @PostMapping("/save/tourattraction")
    public ResponseEntity<List<SavePlaceDto>> saveTourAttraction(
            @RequestBody GoogleRequest googleRequest) {
        List<SavePlaceDto> response = datapipelineService.saveTourAttraction(googleRequest);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "장소 조회 후 태깅, document 저장 (cafe test) ")
    @PostMapping("/save/cafe")
    public ResponseEntity<List<SavePlaceDto>> saveCafe(
            @RequestBody GoogleRequest googleRequest) {
        List<SavePlaceDto> response = datapipelineService.saveCafe(googleRequest);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "장소 조회 후 태깅, document 저장 (restaurant test) ")
    @PostMapping("/save/restaurant")
    public ResponseEntity<List<SavePlaceDto>> saveRestaurant(
            @RequestBody GoogleRequest googleRequest) {
        List<SavePlaceDto> response = datapipelineService.saveRestaurant(googleRequest);

        return ResponseEntity.ok(response);
    }


}