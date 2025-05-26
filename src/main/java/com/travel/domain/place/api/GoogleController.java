package com.travel.domain.place.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travel.domain.categories.entity.Category;
import com.travel.domain.place.dto.PlaceDetailResponse;
import com.travel.domain.place.service.PlaceGoogleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/googleApi")
@Tag(name = "Google", description = "구글service 테스트용 컨트롤러.(실제로는 전부 PlaceService에서 호출)")
public class GoogleController {
    private final PlaceGoogleService googleService;

    @Operation(summary = " 장소 detail 검색, 카테고리 직접 지정해줘야함")
    @GetMapping("/{placeGoogleId}")
    public ResponseEntity<PlaceDetailResponse> getGooglePlaceDetail(
            @PathVariable String placeGoogleId, @RequestParam Category category) {
        return ResponseEntity.ok(googleService.getDetailByPlaceId(placeGoogleId, category));
    }
}
