package com.travel.domain.place.service;


import com.travel.domain.categories.entity.Category;
import com.travel.domain.place.dao.PlaceRepository;
import com.travel.domain.place.dto.*;
import com.travel.domain.place.entity.Place;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final PlacetypeService placetypeService;
    private final PlaceGoogleService placeGoogleService;


    @Transactional
    public PlaceListResponse searchRecommendation(RecommendationRequest recommendationRequest) {
        //setMainPlaceValues로 lng, lat 값 가져올 수 있음.


        List<PlaceResponse> placeResponseList = getCafeList(recommendationRequest.getCafeTagList());
        placeResponseList.addAll(getRestaurantList(recommendationRequest.getRestaurantTypeList()));
        placeResponseList.addAll( getTourattractionList(recommendationRequest.getTourattractionTagList(), recommendationRequest.getSubjectiveTagList()));


        return PlaceListResponse.builder()
                .placeResponseList(placeResponseList)
                .build();
    }


    private PlaceCoordinate setMainPlaceValues(RecommendationRequest recommendationRequest){
        return placeGoogleService.getCoordinateByAddress(recommendationRequest.getMainTourPlace());
    }


            //place.setSubjectiveTags(new ArrayList<>(newTags));

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
    private List<PlaceResponse> getCafeList(List<CafeTag> cafeTagList) {
        return null;
    }

    private List<PlaceResponse> getTourattractionList(List<TourattractionTag> tourattractionTagList, List<SubjectiveTag> subjectiveTagList) {
        return null;
    }

    private List<PlaceResponse> getRestaurantList(List<RestaurantType> restaurantTypeList) {
        return null;
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
                .tourattractionTags(tourattractionDoc.getApiTags())
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
}
