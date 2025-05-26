package com.travel.domain.placetype.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.domain.placetype.dao.cafe.CafeElasticsearchRepository;
import com.travel.domain.placetype.dao.restaurant.RestaurantElasticsearchRepository;
import com.travel.domain.placetype.dao.tourattraction.TourattractionElasticsearchRepository;
import com.travel.domain.placetype.entity.cafe.CafeDoc;
import com.travel.domain.placetype.entity.restaurant.RestaurantDoc;
import com.travel.domain.placetype.entity.tourattraction.TourattractionDoc;
import com.travel.global.common.error.CustomException;
import com.travel.global.common.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlacetypeService {
    private final RestaurantElasticsearchRepository restaurantElasticsearchRepository;
    private final CafeElasticsearchRepository cafeElasticsearchRepository;
    private final TourattractionElasticsearchRepository tourattractionElasticsearchRepository;

    @Transactional
    public RestaurantDoc getRestaurantDocument(String placeGoogleId) {
        return restaurantElasticsearchRepository
                .findByPlaceGoogleId(placeGoogleId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESTAURANTDOC_NOT_FOUND));
    }

    @Transactional
    public CafeDoc getCafeDocument(String placeGoogleId) {
        return cafeElasticsearchRepository
                .findByPlaceGoogleId(placeGoogleId)
                .orElseThrow(() -> new CustomException(ErrorCode.CAFEDOC_NOT_FOUND));
    }

    @Transactional
    public TourattractionDoc getTourattractionDocument(String placeGoogleId) {
        return tourattractionElasticsearchRepository
                .findByPlaceGoogleId(placeGoogleId)
                .orElseThrow(() -> new CustomException(ErrorCode.TOURATTRACTIONDOC_NOT_FOUND));
    }
}
