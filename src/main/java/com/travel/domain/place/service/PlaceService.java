package com.travel.domain.place.service;

import com.travel.domain.place.dto.TourAttractionDetailDto;
import com.travel.domain.place.dto.request.GoogleRequest;
import com.travel.domain.place.dto.TourAttractionListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final GoogleService googleService;
    private final LLMService llmService;

    @Transactional
    public TourAttractionListDto searchPlace(GoogleRequest googleRequest){
            TourAttractionListDto tourAttractionListDto = googleService.searchTourAttraction(googleRequest);
            llmService.generateTagsWithLLM(tourAttractionListDto);
            return tourAttractionListDto;
    }


    public List<TourAttractionDetailDto> searchPlaceDetail(GoogleRequest googleRequest) {
        TourAttractionListDto tourAttractionListDto = googleService.searchTourAttraction(googleRequest);
        return googleService.getDetailedTourAttractions(tourAttractionListDto);
    }
}
