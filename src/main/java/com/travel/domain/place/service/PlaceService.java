package com.travel.domain.place.service;

import java.util.List;
import java.util.stream.Collectors;

import com.travel.domain.llm.dto.TourAttractionReviewDto;
import com.travel.domain.place.dto.TourAttractionLLMDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.domain.place.dto.TourAttractionDetailDto;
import com.travel.domain.place.dto.TourAttractionListDto;
import com.travel.domain.place.dto.request.GoogleRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final GoogleService googleService;
    private final LLMService llmService;

    /*
    정적 키워드로 장소 리스트 반환 함수
     */
    @Transactional
    public TourAttractionListDto searchPlace(GoogleRequest googleRequest) {
        TourAttractionListDto tourAttractionListDto =
                googleService.searchTourAttraction(googleRequest);

        return tourAttractionListDto;
    }

    /*
   정적 키워드로 장소 '세부정보' 리스트 반환 함수
    */
    public List<TourAttractionDetailDto> searchPlaceDetail(GoogleRequest googleRequest) {
        TourAttractionListDto tourAttractionListDto =
                googleService.searchTourAttraction(googleRequest);
        return googleService.getDetailedTourAttractions(tourAttractionListDto);
    }



    /*
    정적 키워드로 장소 세부 정보 및 llm 태깅 정보 포함하는 함수
     */
    public List<TourAttractionLLMDto> saveTourAttraction(GoogleRequest googleRequest) {
        TourAttractionListDto tourAttractionListDto =
                googleService.searchTourAttraction(googleRequest);

       List<TourAttractionDetailDto> tourAttractionDetailDtos = googleService.getDetailedTourAttractions(tourAttractionListDto);



       return llmService.generateTagsWithGemini(tourAttractionDetailDtos);
    }



}
