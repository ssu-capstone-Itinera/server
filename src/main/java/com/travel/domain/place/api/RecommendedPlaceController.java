package com.travel.domain.place.api;


import com.travel.domain.place.dto.PlaceResponse;
import com.travel.domain.place.dto.RecommendedPlaceResponse;
import com.travel.domain.place.service.PlaceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/recommendedPlace")
@RequiredArgsConstructor
@Tag(name = "최근추천목록", description = "최근추천목록")
public class RecommendedPlaceController {

    private final PlaceService placeService;

    @GetMapping("/recent")
    public ResponseEntity<List<RecommendedPlaceResponse>> getRecentRecommendedPlaces(
            @RequestParam(defaultValue = "10") int size
    ) {
        List<RecommendedPlaceResponse> result = placeService.getRecentRecommendedPlaces(size);
        return ResponseEntity.ok(result);
    }


    @PostMapping("/saveRecommended")
    public ResponseEntity<String> saveRecommendedTest(@RequestBody List<PlaceResponse> placeResponseList) {
        try {
            placeService.saveRecommendedPlaces(placeResponseList);
            return ResponseEntity.ok("추천 장소 저장 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("저장 중 오류 발생: " + e.getMessage());
        }
    }

}
