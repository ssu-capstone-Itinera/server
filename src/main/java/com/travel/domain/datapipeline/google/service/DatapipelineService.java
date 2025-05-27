package com.travel.domain.datapipeline.google.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.domain.categories.entity.Category;
import com.travel.domain.datapipeline.google.dto.PlaceDetailDto;
import com.travel.domain.datapipeline.google.dto.PlaceDto;
import com.travel.domain.datapipeline.google.dto.PlaceListDto;
import com.travel.domain.datapipeline.google.dto.TourAttractionLLMDto;
import com.travel.domain.datapipeline.google.dto.request.GoogleRequest;
import com.travel.domain.datapipeline.google.dto.response.SavePlaceDto;
import com.travel.domain.place.dao.PlaceRepository;
import com.travel.domain.place.entity.Place;
import com.travel.domain.place.entity.PlaceDocument;
import com.travel.domain.place.service.PlaceGoogleService;
import com.travel.domain.placetype.dao.cafe.CafeElasticsearchRepository;
import com.travel.domain.placetype.dao.restaurant.RestaurantElasticsearchRepository;
import com.travel.domain.placetype.dao.tourattraction.TourattractionElasticsearchRepository;
import com.travel.domain.placetype.entity.cafe.CafeDoc;
import com.travel.domain.placetype.entity.restaurant.RestaurantDoc;
import com.travel.domain.placetype.entity.tourattraction.TourattractionDoc;

import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class DatapipelineService {
    private final GoogleService googleService;
    private final LLMService llmService;
    private final PlaceRepository placeRepository;
    private final PlaceGoogleService placeGoogleService;
    private final CafeElasticsearchRepository cafeElasticsearchRepository;
    private final RestaurantElasticsearchRepository restaurantElasticsearchRepository;
    private final TourattractionElasticsearchRepository tourattractionElasticsearchRepository;

    /*
    정적 키워드로 장소 리스트 반환 함수
     */
    @Transactional
    public PlaceListDto searchPlace(GoogleRequest googleRequest) {
        PlaceListDto placeListDto = googleService.searchTourAttraction(googleRequest);

        return placeListDto;
    }

    /*
    정적 키워드로 장소 '세부정보' 리스트 반환 함수
     */
    public List<PlaceDetailDto> searchPlaceDetail(GoogleRequest googleRequest) {
        PlaceListDto placeListDto = googleService.searchTourAttraction(googleRequest);

        return googleService.getPlaceDetail(placeListDto);
    }

    /*
    정적 키워드로 장소 세부 정보 및 llm 태깅 정보 포함하는 함수
     */
    public List<TourAttractionLLMDto> searchTourAttractionWithLLM(GoogleRequest googleRequest) {
        PlaceListDto placeListDto = googleService.searchTourAttraction(googleRequest);

        List<PlaceDetailDto> placeDetailDtos = googleService.getPlaceDetail(placeListDto);

        return llmService.generateTagsWithGemini(placeDetailDtos);
    }

    public List<SavePlaceDto> saveTourAttraction(GoogleRequest googleRequest) {
        PlaceListDto placeListDto = googleService.searchTourAttraction(googleRequest);

        List<PlaceDetailDto> placeDetailDtos = getPlaceDetailDtos(placeListDto);

        List<TourAttractionLLMDto> tourAttractionLLMDtos =
                llmService.generateTagsWithGemini(placeDetailDtos);

        return getSaveTourAttractionList(googleRequest, placeDetailDtos, tourAttractionLLMDtos);
    }

    private static List<SavePlaceDto> getSaveTourAttractionList(
            GoogleRequest googleRequest,
            List<PlaceDetailDto> placeDetailDtos,
            List<TourAttractionLLMDto> tourAttractionLLMDtos) {
        return IntStream.range(0, placeDetailDtos.size())
                .mapToObj(
                        i -> {
                            PlaceDetailDto detailDto = placeDetailDtos.get(i);
                            TourAttractionLLMDto llmDto =
                                    i < tourAttractionLLMDtos.size()
                                            ? tourAttractionLLMDtos.get(i)
                                            : null;

                            // Place (MySQL용 엔티티)
                            Place place =
                                    Place.builder()
                                            .category(Category.TOURATTRACTION)
                                            .placeGoogleId(detailDto.getPlaceId())
                                            .name(detailDto.getName())
                                            .lat(detailDto.getLat())
                                            .lng(detailDto.getLng())
                                            .address(detailDto.getAddress())
                                            .rating(detailDto.getRating())
                                            .phoneNumber(detailDto.getPhoneNumber())
                                            .webSite(detailDto.getWebsite())
                                            .openingHours(detailDto.getOpeningHours())
                                            .priceLevel(detailDto.getPriceLevel())
                                            .build();

                            // PlaceDocument (Elasticsearch 문서)
                            PlaceDocument document =
                                    TourattractionDoc.builder()
                                            .placeGoogleId(detailDto.getPlaceId())
                                            .tourattractionTags(
                                                    List.of(googleRequest.getTourattractionTag()))
                                            .subjectiveTags(
                                                    llmDto != null
                                                            ? llmDto.getSubjectiveTags()
                                                            : null)
                                            .address(place.getAddress())
                                            .placeGoogleId(place.getPlaceGoogleId())
                                            .build();

                            return new SavePlaceDto(place, document);
                        })
                .collect(Collectors.toList());
    }

    public List<SavePlaceDto> saveCafe(GoogleRequest googleRequest) {
        PlaceListDto placeListDto = googleService.searchTourAttraction(googleRequest);


        List<PlaceDetailDto> placeDetailDtos = getPlaceDetailDtos(placeListDto);

        log.info("placeDetailDtoList : " + placeDetailDtos.size());

        return getSaveCafeList(googleRequest, placeDetailDtos);
    }

    // 키워드로 cafe 검색 후 -> detail로 상세 세부 tag 가져오는 코드 추가해야함
    private List<SavePlaceDto> getSaveCafeList(
            GoogleRequest googleRequest, List<PlaceDetailDto> placeDetailDtos) {

        return IntStream.range(0, placeDetailDtos.size())
                .mapToObj(
                        i -> {
                            PlaceDetailDto detailDto = placeDetailDtos.get(i);

                            // Place (MySQL용 엔티티)
                            Place place =
                                    Place.builder()
                                            .category(Category.CAFE)
                                            .placeGoogleId(detailDto.getPlaceId())
                                            .name(detailDto.getName())
                                            .lat(detailDto.getLat())
                                            .lng(detailDto.getLng())
                                            .address(detailDto.getAddress())
                                            .rating(detailDto.getRating())
                                            .phoneNumber(detailDto.getPhoneNumber())
                                            .webSite(detailDto.getWebsite())
                                            .openingHours(detailDto.getOpeningHours())
                                            .priceLevel(detailDto.getPriceLevel())
                                            .build();

                            placeRepository.save(place);

                            CafeDoc cafeDoc =
                                    CafeDoc.builder()
                                            .cafeTags(
                                                    placeGoogleService.getCafeTagsByPlaceId(
                                                            detailDto.getPlaceId()))
                                            .address(place.getAddress())
                                            .placeGoogleId(place.getPlaceGoogleId())
                                            .build();

                            log.info(detailDto.getPlaceId() + "placeGoogleService : " +placeGoogleService.getCafeTagsByPlaceId(detailDto.getPlaceId()));


                            PlaceDocument document = cafeDoc;

                            cafeElasticsearchRepository.save(cafeDoc);

                            return new SavePlaceDto(place, document);
                        })
                .collect(Collectors.toList());
    }

    public List<SavePlaceDto> saveRestaurant(GoogleRequest googleRequest) {
        PlaceListDto placeListDto = googleService.searchTourAttraction(googleRequest);

        List<PlaceDetailDto> placeDetailDtos = getPlaceDetailDtos(placeListDto);

        return getSaveRestaurantList(googleRequest, placeDetailDtos);
    }

    private List<SavePlaceDto> getSaveRestaurantList(
            GoogleRequest googleRequest, List<PlaceDetailDto> placeDetailDtos) {
        return IntStream.range(0, placeDetailDtos.size())
                .mapToObj(
                        i -> {
                            PlaceDetailDto detailDto = placeDetailDtos.get(i);

                            // Place (MySQL용 엔티티)
                            Place place =
                                    Place.builder()
                                            .category(Category.RESTAURANT)
                                            .placeGoogleId(detailDto.getPlaceId())
                                            .name(detailDto.getName())
                                            .address(detailDto.getAddress())
                                            .rating(detailDto.getRating())
                                            .lat(detailDto.getLat())
                                            .lng(detailDto.getLng())
                                            .phoneNumber(detailDto.getPhoneNumber())
                                            .webSite(detailDto.getWebsite())
                                            .openingHours(detailDto.getOpeningHours())
                                            .priceLevel(detailDto.getPriceLevel())
                                            .build();

                            placeRepository.save(place);

                            RestaurantDoc restaurantDoc =
                                    RestaurantDoc.builder()
                                            .restaurantType(googleRequest.getRestaurantType())
                                            .address(place.getAddress())
                                            .placeGoogleId(place.getPlaceGoogleId())
                                            .build();

                            PlaceDocument document = restaurantDoc;
                            restaurantElasticsearchRepository.save(restaurantDoc);

                            return new SavePlaceDto(place, document);
                        })
                .collect(Collectors.toList());
    }

    private List<PlaceDetailDto> getPlaceDetailDtos(PlaceListDto placeListDto) {
        // database에 저장된 place 제거
        List<PlaceDto> newPlaceList = new ArrayList<>();
        for (PlaceDto placeDto : placeListDto.getResults()) {
            if (!placeRepository.existsByPlaceGoogleId(placeDto.getPlaceId())) {
                newPlaceList.add(placeDto);
            }
        }

        placeListDto.setResults(newPlaceList);

        List<PlaceDetailDto> placeDetailDtos = googleService.getPlaceDetail(placeListDto);
        return placeDetailDtos;
    }
}
