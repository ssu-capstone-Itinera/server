package com.travel.domain.place.service;

import com.travel.domain.categories.entity.Category;
import com.travel.domain.place.dto.*;
import com.travel.domain.place.entity.Place;
import com.travel.domain.placetype.entity.cafe.CafeTag;
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

    public PlaceCoordinate getCoordinateByAddress(String mainPlace) {
        try {
            URI uri = UriComponentsBuilder.fromUriString(addressSearchUrl)
                    .queryParam("address", mainPlace)
                    .queryParam("language", "ko")
                    .queryParam("key", googleApiKey)
                    .build(false)
                    .encode(StandardCharsets.UTF_8)
                    .toUri();
            log.info("mainPlace 주소 변환 오류:  {}", uri);

            Map<String, Object> apiResponse = WebClient.create()
                    .get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
            log.info("mainPlace 응답: {}", apiResponse);

            List<Map<String, Object>> results = (List<Map<String, Object>>) apiResponse.get("results");
            if (results == null || results.isEmpty()) {
                throw new CustomException(ErrorCode.GOOGLE_API_NO_RESULT);
            }
            Map<String, Object> geometry = (Map<String, Object>) results.get(0).get("geometry");
            Map<String, Object> location = (Map<String, Object>) geometry.get("Location");

            Double lat = (Double) location.get("lat");
            Double lng = (Double) location.get("lng");
            return new PlaceCoordinate(lat, lng);
        } catch (Exception e) {
            log.error("mainPlace 주소 검색 중 geocode API 호출 실패", e);
            throw new CustomException(ErrorCode.GOOGLE_API_CALL_FAILED);
        }
    }

    private PlaceDetailResponse getDetailByPlaceId(String placeId) {
        PlaceDetailResponse placeDetailResponse = new PlaceDetailResponse();
        try {
            URI uri = UriComponentsBuilder.fromUriString(placeUrl)
                    .queryParam("place_id", placeId)
                    .queryParam("language", "ko")
                    .queryParam("key", googleApiKey)
                    .build(false)
                    .encode(StandardCharsets.UTF_8)
                    .toUri();

            log.info("Detail API URI: {}", uri);

            Map<String, Object> apiResponse = WebClient.create()
                    .get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
            setPlaceDetail(placeDetailResponse, apiResponse);
            return placeDetailResponse;
        } catch (Exception e) {
            log.error("Google Detail API 호출 실패: {}", placeId, e);
            return null;
        }
    }
    private void setPlaceDetail(PlaceDetailResponse placeDetailResponse, Map<String, Object> apiResponse) {
        if (apiResponse == null || apiResponse.isEmpty()) return;

        Map<String, Object> result = (Map<String, Object>) apiResponse.get("result");
        if (result == null || result.isEmpty()) return;

        placeDetailResponse.setPlaceGoogleId((String) result.get("place_id"));
        placeDetailResponse.setName((String) result.get("name"));
        placeDetailResponse.setAddress((String) result.get("formatted_address"));
        placeDetailResponse.setLocation((String) result.get("vicinity")); // 또는 "location" 값이 따로 있으면 수정

        Map<String, Object> geometry = (Map<String, Object>) result.get("geometry");
        if (geometry != null) {
            Map<String, Object> location = (Map<String, Object>) geometry.get("location");
            if (location != null) {
                placeDetailResponse.setLat(location.get("lat") != null ? ((Number) location.get("lat")).doubleValue() : 0.0);
                placeDetailResponse.setLng(location.get("lng") != null ? ((Number) location.get("lng")).doubleValue() : 0.0);
            }
        }

        if (result.get("rating") != null) {
            placeDetailResponse.setRating(((Number) result.get("rating")).doubleValue());
        }

        placeDetailResponse.setPhoneNumber((String) result.get("formatted_phone_number"));
        placeDetailResponse.setWebSite((String) result.get("website"));
        placeDetailResponse.setPriceLevel(result.get("price_level") != null
                ? String.valueOf(result.get("price_level")) : null);

        Map<String, Object> openingHours = (Map<String, Object>) result.get("opening_hours");
        if (openingHours != null) {
            placeDetailResponse.setOpeningHours((List<String>) openingHours.get("weekday_text"));
        }

        Map<String, Object> editorialSummary = (Map<String, Object>) result.get("editorial_summary");
        if (editorialSummary != null) {
            placeDetailResponse.setDescription((String) editorialSummary.get("overview"));
        }

        List<Map<String, Object>> rawReviews = (List<Map<String, Object>>) result.get("reviews");
        if (rawReviews != null) {
            List<String> reviewTexts = new ArrayList<>();
            for (Map<String, Object> r : rawReviews) {
                String text = (String) r.get("text");
                if (text != null && !text.isBlank()) {
                    reviewTexts.add(text);
                }
            }
            placeDetailResponse.setReviews(reviewTexts);
        }
    }

    public PlaceDetailResponse getCafeDetailByPlaceId (String placeId) {
        PlaceDetailResponse cafeDetailResponse = getDetailByPlaceId(placeId);
        cafeDetailResponse.setCategory(Category.CAFE);
        List<CafeTag> cafeTags = getCafeTagsByPlaceId(placeId);
        cafeDetailResponse.setCafeTags(cafeTags);
        return cafeDetailResponse;
    }

    private List<CafeTag> getCafeTagsByPlaceId(String placeId) {
        List<CafeTag> cafeTags = new ArrayList<>();

        String fields = String.join(",",
                "dineIn", "curbsidePickup", "reservable",
                "servesBreakfast", "servesLunch", "servesDinner",
                "servesBeer", "servesWine", "servesBrunch", "servesVegetarianFood",
                "menuForChildren", "servesCocktails", "servesDessert",
                "goodForChildren", "allowsDogs", "goodForGroups",
                "parkingOptions"
        );

        URI uri = UriComponentsBuilder.fromUriString("https://places.googleapis.com/v1/places/" + placeId)
                .queryParam("fields", fields)
                .queryParam("key", googleApiKey)
                .build(false)
                .encode(StandardCharsets.UTF_8)
                .toUri();

        try {
            Map<String, Object> apiResponse = WebClient.create()
                    .get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (apiResponse == null || apiResponse.isEmpty()) return cafeTags;

            for (CafeTag tag : CafeTag.values()) {
                if (!tag.getValue().equals("parkingOptions")) {
                    if (Boolean.TRUE.equals(apiResponse.get(tag.getValue()))) {
                        cafeTags.add(tag);
                    }
                }
            }

            Map<String, Object> parkingOptions = (Map<String, Object>) apiResponse.get("parkingOptions");
            if (parkingOptions != null) {
                boolean hasParking = parkingOptions.values().stream()
                        .anyMatch(v -> Boolean.TRUE.equals(v));
                if (hasParking) {
                    cafeTags.add(CafeTag.PARKING_OPTIONS);
                }
            }

        } catch (Exception e) {
            log.error("Google Places v1 API 호출 실패 (카페 태그 조회)", e);
            throw new CustomException(ErrorCode.GOOGLE_API_CALL_FAILED);
        }

        return cafeTags;
    }



}