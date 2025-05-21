package com.travel.domain.datapipeline.google.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.travel.domain.categories.entity.Category;
import com.travel.domain.datapipeline.google.dto.PlaceDto;
import com.travel.domain.datapipeline.google.dto.PlaceLLMDto;
import com.travel.domain.datapipeline.google.dto.request.PlaceDetailRequest;
import com.travel.domain.datapipeline.google.dto.response.SaveTourAttractionDto;
import com.travel.domain.place.entity.Place;
import com.travel.domain.place.entity.PlaceDocument;
import com.travel.domain.placetype.entity.tourattraction.TourattractionDoc;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.domain.datapipeline.google.dto.PlaceDetailDto;
import com.travel.domain.datapipeline.google.dto.PlaceListDto;
import com.travel.domain.datapipeline.google.dto.request.GoogleRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DatapipelineService {
    private final GoogleService googleService;
    private final LLMService llmService;

    /*
    정적 키워드로 장소 리스트 반환 함수
     */
    @Transactional
    public PlaceListDto searchPlace(GoogleRequest googleRequest) {
        return googleService.searchPlace(googleRequest);
    }
    public PlaceListDto searchPlaceByKeyword(GoogleRequest request) {
        return googleService.searchMyPlaceByKeyword(request);
    }

    public PlaceListDto searchPlaceByAddress(GoogleRequest request) {
        return googleService.searchMyPlaceByAddress(request);
    }

    /*
   정적 키워드로 장소 '세부정보' 리스트 반환 함수
    */
    public List<PlaceDetailDto> searchPlaceDetail(PlaceDetailRequest request) {
        return googleService.getPlaceDetailsByPlaceIds(request.getPlaceIds());
    }


    /*
    정적 키워드로 장소 세부 정보 및 llm 태깅 정보 포함하는 함수
     */
    public List<PlaceLLMDto> searchTourAttractionWithLLM(GoogleRequest googleRequest) {
        PlaceListDto placeListDto = googleService.searchPlace(googleRequest);
        List<String> placeIds = placeListDto.getResults().stream()
                .map(PlaceDto::getPlaceId)
                .filter(Objects::nonNull)
                .toList();
        List<PlaceDetailDto> placeDetailDtos = googleService.getPlaceDetailsByPlaceIds(placeIds);

        return llmService.generateTagsWithGemini(placeDetailDtos);
    }

    public List<SaveTourAttractionDto> saveTourAttraction(GoogleRequest googleRequest){
        PlaceListDto placeListDto = googleService.searchPlace(googleRequest);
        List<String> placeIds = placeListDto.getResults().stream()
                .map(PlaceDto::getPlaceId)
                .filter(Objects::nonNull)
                .toList();
        List<PlaceDetailDto> placeDetailDtos = googleService.getPlaceDetailsByPlaceIds(placeIds);

        List<PlaceLLMDto> placeLLMDtos = llmService.generateTagsWithGemini(placeDetailDtos);
        return getSaveTourAttractionList(googleRequest, placeDetailDtos, placeLLMDtos);
    }

    //
    private static List<SaveTourAttractionDto> getSaveTourAttractionList(
            GoogleRequest googleRequest,
            List<PlaceDetailDto> placeDetailDtos,
            List<PlaceLLMDto> placeLLMDtos) {
        return IntStream.range(0, placeDetailDtos.size())
                .mapToObj(i -> {
                    PlaceDetailDto detailDto = placeDetailDtos.get(i);
                    PlaceLLMDto llmDto = i < placeLLMDtos.size()
                            ? placeLLMDtos.get(i)
                            : null;

                    // Place (MySQL용 엔티티)
                    Place place = Place.builder()
                            .category(Category.TOURATTRACTION)
                            .placeGoogleId(detailDto.getPlaceId())
                            .name(detailDto.getName())
                            .address(detailDto.getAddress())
                            .rating(detailDto.getRating())
                            .phoneNumber(detailDto.getPhoneNumber())
                            .webSite(detailDto.getWebsite())
                            .openingHours(detailDto.getOpeningHours())
                            .priceLevel(detailDto.getPriceLevel().toString())
                            .build();

                    // PlaceDocument (Elasticsearch 문서)
                    PlaceDocument document = TourattractionDoc.builder()
                            .placeGoogleId(detailDto.getPlaceId())
                            .apiTags(List.of(googleRequest.getKeyword()))
                            .subjectiveTags(llmDto != null ? llmDto.getSubjectiveTags() : null)
                            .build();

                    return new SaveTourAttractionDto(place, document);
                })
                .collect(Collectors.toList());
    }
}
