package com.travel.domain.place.api;

import com.travel.domain.place.dto.PlaceDetailResponse;
import com.travel.domain.place.service.PlaceGoogleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/googleApi")
@Tag(name = "Google", description = "구글service 테스트용 컨트롤러.(실제로는 전부 PlaceService에서 호출)")

public class GoogleController {
    private final PlaceGoogleService googleService;

    @Operation(summary = "카페 detail 검색 api(태그포함)")
    @GetMapping("/{placeGoogleId}")
    public ResponseEntity<PlaceDetailResponse> getCafeDetail(@PathVariable String placeGoogleId){
        return ResponseEntity.ok(googleService.getCafeDetailByPlaceId(placeGoogleId));
    }

}
