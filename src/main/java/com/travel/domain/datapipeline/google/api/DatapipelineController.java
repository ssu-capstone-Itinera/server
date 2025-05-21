package com.travel.domain.datapipeline.google.api;

import java.util.List;

import com.travel.domain.datapipeline.google.dto.PlaceLLMDto;
import com.travel.domain.datapipeline.google.dto.request.PlaceDetailRequest;
import com.travel.domain.datapipeline.google.dto.response.SaveTourAttractionDto;
import com.travel.domain.placetype.entity.ApiTag;
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

    @Operation(summary = "장소 조회")
    @PostMapping("/GoogleSearch")
    public ResponseEntity<PlaceListDto> searchGooglePlace(
            @RequestBody GoogleRequest googleRequest) {
        PlaceListDto response = new PlaceListDto();
        googleRequest.setLat(37.56);    //임시값.
        googleRequest.setLng(127.00);   //임시값
        googleRequest.setRadius(5000);  //임시값
        if (googleRequest.getPlaceType().equals("tourist_attraction")
                ||googleRequest.getPlaceType().equals("restaurant")
                ||googleRequest.getPlaceType().equals("cafe")) {
            for(ApiTag keyword : googleRequest.getKeywords()){
                googleRequest.setKeyword(keyword);
                response.getResults().addAll(datapipelineService.searchPlace(googleRequest).getResults());
            }
        }

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "직접 장소 검색")
    @PostMapping("/MyplaceSearch")
    public ResponseEntity<PlaceListDto> searchMyplace(
            @RequestBody GoogleRequest googleRequest){
        PlaceListDto response = new PlaceListDto();
        googleRequest.setLat(37.56);    //임시값.
        googleRequest.setLng(127.00);   //임시값
        googleRequest.setRadius(5000);  //임시값
        if(googleRequest.getPlaceType().equals("myPlace_keyword")){
            response.getResults().addAll(datapipelineService.searchPlaceByKeyword(googleRequest).getResults());
        }else if(googleRequest.getPlaceType().equals("myPlace_address")){
            response.getResults().addAll(datapipelineService.searchPlaceByAddress(googleRequest).getResults());
        }
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "장소 상세 조회")
    @PostMapping("/GoogleSearch/detail")
    public ResponseEntity<List<PlaceDetailDto>> searchDetailPlace(
            @RequestBody PlaceDetailRequest request) {
        List<PlaceDetailDto> response = datapipelineService.searchPlaceDetail(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "장소 조회 후 태깅 (tourAttraction test) ")
    @PostMapping("/llm/tagging")
    public ResponseEntity<List<PlaceLLMDto>> getLLMTagging(
            @RequestBody GoogleRequest googleRequest) {
        List<PlaceLLMDto> response = datapipelineService.searchTourAttractionWithLLM(googleRequest);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "장소 조회 후 태깅, document 저장 (tourAttraction test) ")
    @PostMapping("/tourattraction/save")
    public ResponseEntity<List<SaveTourAttractionDto>> saveTourAttraction(
            @RequestBody GoogleRequest googleRequest) {
        List<SaveTourAttractionDto> response = datapipelineService.saveTourAttraction(googleRequest);

        return ResponseEntity.ok(response);
    }

}
