package com.travel.domain.place.service;


import com.travel.domain.categories.entity.Category;
import com.travel.domain.datapipeline.google.dto.request.GoogleRequest;
import com.travel.domain.datapipeline.google.dto.response.SavePlaceDto;
import com.travel.domain.datapipeline.google.service.DatapipelineService;
import com.travel.domain.place.dao.PlaceRepository;
import com.travel.domain.place.dto.*;
import com.travel.domain.place.entity.Place;
import com.travel.domain.placetype.dao.cafe.CafeElasticsearchRepository;
import com.travel.domain.placetype.dao.restaurant.RestaurantElasticsearchRepository;
import com.travel.domain.placetype.dao.tourattraction.TourattractionElasticsearchRepository;
import com.travel.domain.placetype.dto.response.CafeResponse;
import com.travel.domain.placetype.dto.response.RestaurantResponse;
import com.travel.domain.placetype.dto.response.TourattractionResponse;
import com.travel.domain.placetype.entity.cafe.CafeDoc;
import com.travel.domain.placetype.entity.cafe.CafeTag;
import com.travel.domain.placetype.entity.restaurant.RestaurantDoc;
import com.travel.domain.placetype.entity.restaurant.RestaurantType;
import com.travel.domain.placetype.entity.tourattraction.TourattractionTag;
import com.travel.domain.placetype.entity.tourattraction.SubjectiveTag;
import com.travel.domain.placetype.entity.tourattraction.TourattractionDoc;
import com.travel.domain.placetype.service.PlacetypeService;
import com.travel.global.common.error.CustomException;
import com.travel.global.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final PlaceRankingService placeRankingService;
    private final PlacetypeService placetypeService;
    private final PlaceGoogleService placeGoogleService;
    private final DatapipelineService datapipelineService;

    private final RestaurantElasticsearchRepository restaurantElasticsearchRepository;
    private final CafeElasticsearchRepository cafeElasticsearchRepository;
    private final TourattractionElasticsearchRepository tourattractionElasticsearchRepository;

    private final int PAGE_SIZE = 20;
    private final int HOME_LIMIT = 20;


    @Transactional
    public PlaceListResponse searchRecommendation(RecommendationRequest recommendationRequest) {
        //setMainPlaceValues로 lng, lat 값 가져올 수 있음.

        String mainTourPlace = recommendationRequest.getMainTourPlace();

        PlaceCoordinate placeCoordinate = setMainPlaceValues(recommendationRequest);

        List<PlaceResponse> placeResponseList = new ArrayList<>();
        if(!recommendationRequest.getCafeTagList().isEmpty()) {
            placeResponseList.addAll(getCafeList(mainTourPlace, placeCoordinate, recommendationRequest.getCafeTagList()));
        }
        if(!recommendationRequest.getRestaurantTypeList().isEmpty()) {
            placeResponseList.addAll(getRestaurantList(mainTourPlace, placeCoordinate, recommendationRequest.getRestaurantTypeList()));
        }
        if(!(recommendationRequest.getTourattractionTagList().isEmpty() && recommendationRequest.getSubjectiveTagList().isEmpty())) {
            placeResponseList.addAll(getTourattractionList(mainTourPlace, placeCoordinate, recommendationRequest.getTourattractionTagList(), recommendationRequest.getSubjectiveTagList()));
        }


        return PlaceListResponse.builder()
                .placeResponseList(placeResponseList)
                .build();
    }


    private PlaceCoordinate setMainPlaceValues(RecommendationRequest recommendationRequest){
        return placeGoogleService.getCoordinateByAddress(recommendationRequest.getMainTourPlace());
    }


            //place.setSubjectiveTags(new ArrayList<>(newTags));

    @Transactional
    public MyPlaceResponse searchMyPlace(MyPlaceRequest myPlaceRequest){
        if(myPlaceRequest.getSearchType().equals("myPlace_keyword")){
            return placeGoogleService.getPlaceByKeyword(myPlaceRequest);
        }else if(myPlaceRequest.getSearchType().equals("myPlace_address")){
            return placeGoogleService.getPlaceByAddress(myPlaceRequest);
        }else throw new CustomException(ErrorCode.INVALID_MYPLACE_SEARCH_TYPE);
    }
    public MyPlaceResponse selectMyPlace(MyPlaceSelectRequest myPlaceSelectRequest) {
        myPlaceSelectRequest.getMyPlace().setName(myPlaceSelectRequest.getCustomName());

        List<Place> myPlace = new ArrayList<>();
        myPlace.add(myPlaceSelectRequest.getMyPlace());
        return new MyPlaceResponse(myPlace);
    }

    /*
    tag 기반 -> elastic search에서 정보 가져오기
    코드 로직 추가 예정
     */

    private List<PlaceResponse> getCafeList(String mainTourPlace, PlaceCoordinate placeCoordinate, List<CafeTag> cafeTagList) {
        List<String> getPlaceId = new ArrayList<>();
        for(CafeTag cafeTag : cafeTagList){
            List<CafeDoc> cafeDocList = cafeElasticsearchRepository.findCafesByAddressAndCafeTags(mainTourPlace, cafeTag);
            List<String> getCafeList = cafeDocList.stream()
                    .map(CafeDoc::getPlaceGoogleId)
                    .collect(Collectors.toList());

            log.info("database "+ getCafeList.toString());
            if(getCafeList.isEmpty()){
                GoogleRequest googleRequest = GoogleRequest.builder()
                        .category(Category.CAFE)
                        .lng(placeCoordinate.getLng())
                        .lat(placeCoordinate.getLat())
                        .cafeTag(cafeTag)
                        .maxResults(100)
                        .build();
                List<SavePlaceDto> savePlaceDtoList =  datapipelineService.saveCafe(googleRequest);
                log.info("saveDto : "+ savePlaceDtoList.size());
                getCafeList = savePlaceDtoList.stream()
                        .map(dto -> dto.getPlace().getPlaceGoogleId())
                        .filter(Objects::nonNull)
                        .distinct()
                        .collect(Collectors.toList());
            }

            log.info("database 1111"+ getCafeList.size());


            getPlaceId.addAll(getCafeList);

        }
        return getPlaceResponseList(getPlaceId, Category.CAFE);
    }

    private List<PlaceResponse> getTourattractionList(String mainTourPlace, PlaceCoordinate placeCoordinate, List<TourattractionTag> tourattractionTagList, List<SubjectiveTag> subjectiveTagList) {
        List<String> getPlaceId = new ArrayList<>();
        log.info("touattraction");
        for(TourattractionTag tourattractionTag : tourattractionTagList){
            List<TourattractionDoc> tourattractionDocList = tourattractionElasticsearchRepository.findPlaceGoogleIdsByAddressAndTourattractionTags(mainTourPlace, tourattractionTag);

            List<String> getRestaurantList = tourattractionDocList.stream()
                    .map(TourattractionDoc::getPlaceGoogleId)
                    .collect(Collectors.toList());

            if(getRestaurantList.isEmpty()){
                GoogleRequest googleRequest = GoogleRequest.builder()
                        .category(Category.TOURATTRACTION)
                        .lng(placeCoordinate.getLng())
                        .lat(placeCoordinate.getLat())
                        .tourattractionTag(tourattractionTag)
                        .maxResults(100)
                        .build();
                List<SavePlaceDto> savePlaceDtoList =  datapipelineService.saveTourAttraction(googleRequest);
                getRestaurantList = savePlaceDtoList.stream()
                        .map(dto -> dto.getPlace().getPlaceGoogleId())
                        .filter(Objects::nonNull)
                        .distinct()
                        .collect(Collectors.toList());
            }

            getPlaceId.addAll(getRestaurantList);

        }

        for(SubjectiveTag subjectiveTag : subjectiveTagList){
            List<TourattractionDoc> tourattractionDocList = tourattractionElasticsearchRepository.findPlaceGoogleIdsByAddressAndSubjectiveTags(mainTourPlace, subjectiveTag);

            List<String> getRestaurantList = tourattractionDocList.stream()
                    .map(TourattractionDoc::getPlaceGoogleId)
                    .filter(placeGoogleId -> !getPlaceId.contains(placeGoogleId)) // 포함 안된 경우만 필터링
                    .collect(Collectors.toList());


            getPlaceId.addAll(getRestaurantList);

        }
        return getPlaceResponseList(getPlaceId, Category.TOURATTRACTION);
    }

    private List<PlaceResponse> getRestaurantList(String mainTourPlace, PlaceCoordinate placeCoordinate, List<RestaurantType> restaurantTypeList) {


        List<String> getPlaceId = new ArrayList<>();
        for(RestaurantType restaurantType : restaurantTypeList){

            List<RestaurantDoc> restaurantDocList = restaurantElasticsearchRepository.findRestaurantsByAddressAndRestaurantType(mainTourPlace, restaurantType);
            List<String> getRestaurantList = restaurantDocList.stream()
                    .map(RestaurantDoc::getPlaceGoogleId)
                    .collect(Collectors.toList());

            log.info("getRestaurantList List " + getRestaurantList.size());
            if(getRestaurantList.isEmpty()){
                GoogleRequest googleRequest = GoogleRequest.builder()
                        .category(Category.RESTAURANT)
                        .lng(placeCoordinate.getLng())
                        .lat(placeCoordinate.getLat())
                        .restaurantType(restaurantType)
                        .maxResults(100)
                        .build();
                List<SavePlaceDto> savePlaceDtoList =  datapipelineService.saveRestaurant(googleRequest);
                log.info("savePlaceDto List " + savePlaceDtoList.size());
                getRestaurantList = savePlaceDtoList.stream()
                        .map(dto -> dto.getPlace().getPlaceGoogleId())
                        .filter(Objects::nonNull)
                        .distinct()
                        .collect(Collectors.toList());
            }

            getPlaceId.addAll(getRestaurantList);

        }
        return getPlaceResponseList(getPlaceId, Category.RESTAURANT);
    }

    private List<PlaceResponse>  getPlaceResponseList(List<String> getPlaceId, Category category) {
        return getPlaceId.stream()
                .map(googleId -> {

                    log.info("the problem placeId is : " + googleId);
                    Place place = placeRepository.findByPlaceGoogleIdOrElseThrow(googleId);
                    return PlaceResponse.builder()
                            .placeId(place.getId())
                            .placeGoogleId(googleId)
                            .lng(place.getLng())
                            .lat(place.getLat())
                            .address(place.getAddress())
                            .rating(place.getRating())
                            .category(category)
                            .name(place.getName())
                            .build();
                })
                .collect(Collectors.toList());
    }


    @Transactional
    public PlaceDetailResponse getPlaceDetail(String placeGoogleId) {
        Place place = placeRepository.findByPlaceGoogleId(placeGoogleId)
                .orElseThrow(() -> new CustomException(ErrorCode.PLACE_NOT_FOUND));

        if(place.getCategory().equals(Category.CAFE)){
            return getCafeDetail(place);
        }else if(place.getCategory().equals(Category.RESTAURANT)){
            return getRestaurantDetail(place);
        }else if(place.getCategory().equals(Category.TOURATTRACTION)){
            return getTourattractionDetail(place);
        }

        return PlaceDetailResponse.builder()
                .id(place.getId())
                .placeGoogleId(place.getPlaceGoogleId())
                .category(place.getCategory())
                .name(place.getName())
                .address(place.getAddress())
                .lng(place.getLng())
                .lat(place.getLat())
                .location(place.getLocation())
                .webSite(place.getWebSite())
                .rating(place.getRating())
                .openingHours(place.getOpeningHours())
                .priceLevel(place.getPriceLevel())
                .reviews(place.getReviews())
                .build();
    }

    private PlaceDetailResponse getTourattractionDetail(Place place) {
        TourattractionDoc tourattractionDoc = placetypeService.getTourattractionDocument(place.getPlaceGoogleId());
        return PlaceDetailResponse.builder()
                .id(place.getId())
                .placeGoogleId(place.getPlaceGoogleId())
                .category(place.getCategory())
                .name(place.getName())
                .address(place.getAddress())
                .lng(place.getLng())
                .lat(place.getLat())
                .location(place.getLocation())
                .webSite(place.getWebSite())
                .rating(place.getRating())
                .openingHours(place.getOpeningHours())
                .priceLevel(place.getPriceLevel())
                .reviews(place.getReviews())
                .tourattractionTags(tourattractionDoc.getTourattractionTags())
                .subjectiveTags(tourattractionDoc.getSubjectiveTags())
                .build();

    }

    private PlaceDetailResponse getRestaurantDetail(Place place) {
        RestaurantDoc restaurantDoc = placetypeService.getRestaurantDocument(place.getPlaceGoogleId());
        return PlaceDetailResponse.builder()
                .id(place.getId())
                .placeGoogleId(place.getPlaceGoogleId())
                .category(place.getCategory())
                .name(place.getName())
                .address(place.getAddress())
                .lng(place.getLng())
                .lat(place.getLat())
                .location(place.getLocation())
                .webSite(place.getWebSite())
                .rating(place.getRating())
                .openingHours(place.getOpeningHours())
                .priceLevel(place.getPriceLevel())
                .reviews(place.getReviews())
                .restaurantType(restaurantDoc.getRestaurantType())
                .build();
    }

    private PlaceDetailResponse getCafeDetail(Place place) {
        CafeDoc cafeDoc = placetypeService.getCafeDocument(place.getPlaceGoogleId());
        return PlaceDetailResponse.builder()
                .id(place.getId())
                .placeGoogleId(place.getPlaceGoogleId())
                .category(place.getCategory())
                .name(place.getName())
                .address(place.getAddress())
                .lng(place.getLng())
                .lat(place.getLat())
                .location(place.getLocation())
                .webSite(place.getWebSite())
                .rating(place.getRating())
                .openingHours(place.getOpeningHours())
                .priceLevel(place.getPriceLevel())
                .reviews(place.getReviews())
                .cafeTags(cafeDoc.getCafeTags())
                .build();
    }

    public PlaceListResponse getPopularPlaces() {

        List<Place> placeList = placeRankingService.getTopPlaces(HOME_LIMIT);
        List<PlaceResponse> placeResponseList = placeList.stream()
                .map(place -> {
                    return PlaceResponse.builder()
                            .placeId(place.getId())
                            .placeGoogleId(place.getPlaceGoogleId())
                            .lng(place.getLng())
                            .lat(place.getLat())
                            .address(place.getAddress())
                            .rating(place.getRating())
                            .category(place.getCategory())
                            .photo(place.getPhoto())
                            .name(place.getName())
                            .build();
                })
                .collect(Collectors.toList());

        return PlaceListResponse.builder()
                .placeResponseList(placeResponseList)
                .build();
    }
}
