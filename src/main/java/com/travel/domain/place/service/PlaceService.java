package com.travel.domain.place.service;


import com.travel.domain.categories.entity.Category;
import com.travel.domain.place.dao.PlaceRepository;
import com.travel.domain.place.dto.PlaceDetailResponse;
import com.travel.domain.place.dto.PlaceResponse;
import com.travel.domain.place.dto.RecommendationRequest;
import com.travel.domain.place.entity.Place;
import com.travel.domain.placetype.dto.response.CafeResponse;
import com.travel.domain.placetype.dto.response.RestaurantResponse;
import com.travel.domain.placetype.dto.response.TourattractionResponse;
import com.travel.domain.placetype.entity.cafe.CafeDoc;
import com.travel.domain.placetype.entity.cafe.CafeTag;
import com.travel.domain.placetype.entity.restaurant.RestaurantDoc;
import com.travel.domain.placetype.entity.restaurant.RestaurantType;
import com.travel.domain.placetype.entity.tourattraction.ApiTag;
import com.travel.domain.placetype.entity.tourattraction.SubjectiveTag;
import com.travel.domain.placetype.entity.tourattraction.TourattractionDoc;
import com.travel.domain.placetype.service.PlacetypeService;
import com.travel.global.common.error.CustomException;
import com.travel.global.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final PlacetypeService placetypeService;


    @Transactional
    public PlaceResponse searchRecommendation(RecommendationRequest recommendationRequest) {
        List<CafeResponse> cafeResponseList = getCafeList(recommendationRequest.getCafeTagList());
        List<RestaurantResponse> restaurantResponseList = getRestaurantList(recommendationRequest.getRestaurantTypeList());
        List<TourattractionResponse> tourattractionResponseList = getTourattractionList(recommendationRequest.getApiTagList(), recommendationRequest.getSubjectiveTagList());

        return PlaceResponse.builder()
                .cafeResponseList(cafeResponseList)
                .restaurantResponseList(restaurantResponseList)
                .tourattractionResponseList(tourattractionResponseList)
                .build();
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
        //saveMyPlaceToUserDatabase(myPalceSelectRequset.getMyPlace());

    }

    /*
    tag 기반 -> elastic search에서 정보 가져오기
    코드 로직 추가 예정
     */
    private List<CafeResponse> getCafeList(List<CafeTag> cafeTagList) {
        return null;
    }

    private List<TourattractionResponse> getTourattractionList(List<ApiTag> apiTagList, List<SubjectiveTag> subjectiveTagList) {
        return null;
    }

    private List<RestaurantResponse> getRestaurantList(List<RestaurantType> restaurantTypeList) {
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
                .apiTags(tourattractionDoc.getApiTags())
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
