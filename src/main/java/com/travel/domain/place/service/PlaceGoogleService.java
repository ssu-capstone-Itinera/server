package com.travel.domain.place.service;


import com.travel.domain.place.dto.*;
import com.travel.domain.place.entity.Place;
import com.travel.global.common.error.CustomException;
import com.travel.global.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceGoogleService {

    @Value("${google.api.key}")
    private String googleApiKey;

    @Value("${google.api.nearbysearch-url}")
    private String nearBySearchUrl;

    @Value("${google.api.place-url}")
    private String placeUrl;

    @Value("${google.api.textsearch-url}")
    private String textSearchUrl;

    @Value("${google.api.addresssearch-url}")
    private String addressSearchUrl;

    public MyPlaceResponse getPlaceByKeyword(MyPlaceRequest myPlaceRequest){
        URI uri = UriComponentsBuilder.fromUriString(textSearchUrl)
                .queryParam("query", myPlaceRequest.getMyPlaceQuery())
                .queryParam("language", "ko")
                .queryParam("key", googleApiKey)
                .build(false)
                .encode(StandardCharsets.UTF_8)
                .toUri();
        log.info("myPlace_keyword url:  {}", uri);

        Map<String, Object> apiResponse = WebClient.create()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        log.info("myPlace_keyword 응답: {}", apiResponse);

        List<Map<String, Object>> results = (List<Map<String, Object>>) apiResponse.get("results");
        if (results == null || results.isEmpty()) {
            throw new CustomException(ErrorCode.GOOGLE_API_NO_RESULT);
        }
        return getMyPlace(results);
    }

    public MyPlaceResponse getPlaceByAddress(MyPlaceRequest myPlaceRequest){
        try {
            URI uri = UriComponentsBuilder.fromUriString(addressSearchUrl)
                    .queryParam("address", myPlaceRequest.getMyPlaceAddress())
                    .queryParam("language", "ko")
                    .queryParam("key", googleApiKey)
                    .build(false)
                    .encode(StandardCharsets.UTF_8)
                    .toUri();
            log.info("myPlace_address url:  {}", uri);

            Map<String, Object> apiResponse = WebClient.create()
                    .get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
            log.info("myPlace_address 응답: {}", apiResponse);

            List<Map<String, Object>> results = (List<Map<String, Object>>) apiResponse.get("results");
            if (results == null || results.isEmpty()) {
                throw new CustomException(ErrorCode.GOOGLE_API_NO_RESULT);
            }
            return getMyPlace(results);
        } catch (Exception e) {
            log.error("mainPlace 주소 검색 중 geocode API 호출 실패", e);
            throw new CustomException(ErrorCode.GOOGLE_API_CALL_FAILED);
        }

    }

    private MyPlaceResponse getMyPlace(List<Map<String, Object>> results) {
        List<Place> placeList = new ArrayList<>();

        for (Map<String, Object> result : results) {
            String placeId = (String) result.get("place_id");

            Map<String, Object> geometry = (Map<String, Object>) result.get("geometry");
            Map<String, Object> location = geometry != null ? (Map<String, Object>) geometry.get("location") : null;

            Double lat = location != null ? (Double) location.get("lat") : null;
            Double lng = location != null ? (Double) location.get("lng") : null;

            String name;
            if (result.containsKey("formatted_address") && !result.containsKey("name")) {
                name = (String) result.get("formatted_address");
            } else {
                name = (String) result.get("name");
            }

            Place place = Place.builder()
                    .placeGoogleId(placeId)
                    .name(name)
                    .lat(lat)
                    .lng(lng)
                    .build();

            placeList.add(place);
        }

        return new MyPlaceResponse(placeList);
    }



}