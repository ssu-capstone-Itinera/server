package com.travel.domain.datapipeline.google.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.travel.domain.categories.entity.Category;
import com.travel.domain.datapipeline.google.dto.TourAttractionLLMDto;
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
        PlaceListDto placeListDto =
                googleService.searchTourAttraction(googleRequest);

        return placeListDto;
    }

    /*
   정적 키워드로 장소 '세부정보' 리스트 반환 함수
    */
    public List<PlaceDetailDto> searchPlaceDetail(GoogleRequest googleRequest) {
        PlaceListDto placeListDto =
                googleService.searchTourAttraction(googleRequest);

        return googleService.getDetailedTourAttractions(placeListDto);
    }



    /*
    정적 키워드로 장소 세부 정보 및 llm 태깅 정보 포함하는 함수
     */
    public List<TourAttractionLLMDto> searchTourAttractionWithLLM(GoogleRequest googleRequest) {
        PlaceListDto placeListDto =
                googleService.searchTourAttraction(googleRequest);

        List<PlaceDetailDto> placeDetailDtos = googleService.getDetailedTourAttractions(placeListDto);

        return llmService.generateTagsWithGemini(placeDetailDtos);
    }

    public List<SaveTourAttractionDto> saveTourAttraction(GoogleRequest googleRequest){
        PlaceListDto placeListDto =
                googleService.searchTourAttraction(googleRequest);

        List<PlaceDetailDto> placeDetailDtos = googleService.getDetailedTourAttractions(placeListDto);


        List<TourAttractionLLMDto> tourAttractionLLMDtos = llmService.generateTagsWithGemini(placeDetailDtos);


        List<SaveTourAttractionDto> saveTourAttractionDtoList = getSaveTourAttractionList(googleRequest, placeDetailDtos, tourAttractionLLMDtos);


        return saveTourAttractionDtoList;
    }


    //
    private static List<SaveTourAttractionDto> getSaveTourAttractionList(
            GoogleRequest googleRequest,
            List<PlaceDetailDto> placeDetailDtos,
            List<TourAttractionLLMDto> tourAttractionLLMDtos
    ) {
        return IntStream.range(0, placeDetailDtos.size())
                .mapToObj(i -> {
                    PlaceDetailDto detailDto = placeDetailDtos.get(i);
                    TourAttractionLLMDto llmDto = i < tourAttractionLLMDtos.size()
                            ? tourAttractionLLMDtos.get(i)
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
                            .priceLevel(detailDto.getPriceLevel())
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

    public PlaceListDto searchPlaceByKeyword(GoogleRequest request) {
        return googleService.searchMyPlaceByKeyword(request);
    }

    public PlaceListDto searchPlaceByAddress(GoogleRequest request) {
        return googleService.searchMyPlaceByAddress(request);
    }

}