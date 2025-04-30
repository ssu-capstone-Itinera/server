package com.travel.domain.datapipeline.google.service;

import com.travel.domain.datapipeline.google.dto.request.GoogleRequest;
import com.travel.domain.datapipeline.google.dto.PlaceDto;
import com.travel.domain.datapipeline.google.dto.PlaceDetailDto;
import com.travel.domain.datapipeline.google.dto.TourAttractionListDto;
import com.travel.global.common.error.CustomException;
import com.travel.global.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleService {

    @Value("${google.api.key}")
    private String googleApiKey;

    @Value("${google.api.nearbysearch-url}")
    private String nearBySearchUrl;

    @Value("${google.api.place-url}")
    private String placeUrl;

    public TourAttractionListDto searchTourAttraction(GoogleRequest googleRequest) {
        String type = "tourist_attraction";
        try {
            Map<String, Object> apiResponse = WebClient.create(nearBySearchUrl)
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("location", googleRequest.getLat() + "," + googleRequest.getLng())
                            .queryParam("radius", 5000)
                            .queryParam("keyword", googleRequest.getKeyword().getValue())
                            .queryParam("type", type)
                            .queryParam("language", "ko")
                            .queryParam("key", googleApiKey)
                            .build())
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            log.info("Google API 응답 받음: {}", apiResponse);


            // Map에서 TourAttractionResponse로 변환
            TourAttractionListDto response = getTourAttractionListDto(googleRequest, apiResponse);

            return response;

        } catch (Exception e) {
            log.error("Google Places API 호출 중 오류 발생: ", e);
            throw new CustomException(ErrorCode.GOOGLE_API_CALL_FAILED);
        }
    }

    private TourAttractionListDto getTourAttractionListDto(GoogleRequest googleRequest, Map<String, Object> apiResponse) {
        TourAttractionListDto response = new TourAttractionListDto();

        if (apiResponse != null) {
            response.setNextPageToken((String) apiResponse.get("next_page_token"));

            List<Map<String, Object>> results = (List<Map<String, Object>>) apiResponse.get("results");

            //TourAttractionListDto의 placeList 생성
            List<PlaceDto> placeList = getPlaceDtos(results);

            response.setResults(placeList);


            getDetailedTourAttractions(response);
        }
        return response;
    }

    private static List<PlaceDto> getPlaceDtos(List<Map<String, Object>> results) {
        List<PlaceDto> placeList = new ArrayList<>();


        if (results != null) {
            for (Map<String, Object> result : results) {
                PlaceDto place = new PlaceDto();
                place.setName((String) result.get("name"));
                place.setVicinity((String) result.get("vicinity"));
                place.setPlaceId((String) result.get("place_id"));
                place.setIcon((String) result.get("icon"));
                place.setBusinessStatus((String) result.get("business_status"));

                // 평점 처리
                if (result.get("rating") != null) {
                    place.setRating(Double.valueOf(result.get("rating").toString()));
                }

                // 위치 정보 처리
                Map<String, Object> geometry = (Map<String, Object>) result.get("geometry");
                if (geometry != null) {
                    Map<String, Object> location = (Map<String, Object>) geometry.get("location");
                    if (location != null) {
                        place.setLat(Double.valueOf(location.get("lat").toString()));
                        place.setLng(Double.valueOf(location.get("lng").toString()));
                    }
                }

                // 영업 시간 처리
                Map<String, Object> openingHours = (Map<String, Object>) result.get("opening_hours");
                if (openingHours != null) {
                    place.setOpenNow((Boolean) openingHours.get("open_now"));
                }

                // 사진 참조 처리
                List<Map<String, Object>> photos = (List<Map<String, Object>>) result.get("photos");
                if (photos != null && !photos.isEmpty()) {
                    place.setPhotoReference((String) photos.get(0).get("photo_reference"));
                }

                placeList.add(place);
            }
        }
        return placeList;
    }

    public List<PlaceDetailDto> getDetailedTourAttractions(TourAttractionListDto tourAttractionListDto) {
        return tourAttractionListDto.getResults().stream()
                .map(PlaceDto::getPlaceId)
                .map(this::getDetailByPlaceId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private PlaceDetailDto getDetailByPlaceId(String placeId) {
        try {
            Map<String, Object> response = WebClient.create(placeUrl)
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("place_id", placeId)
                            .queryParam("key", googleApiKey)
                            .queryParam("language", "ko")
                            .queryParam("fields", "name,formatted_address,photos,opening_hours,website,reviews,rating,price_level,formatted_phone_number")
                            .build())
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            log.info("place detail Google API 응답 받음: {}", response);

            Map<String, Object> result = (Map<String, Object>) response.get("result");

            if (result == null) return null;

            return getPlaceDetailDto(placeId, result);

        } catch (Exception e) {
            log.error("WebClient call failed for placeId={}", placeId, e);

            return null;
        }
    }

    private static PlaceDetailDto getPlaceDetailDto(String placeId, Map<String, Object> result) {
        // 장소 사진 처리
        List<String> photos = new ArrayList<>();
        List<Map<String, Object>> photoRefs = (List<Map<String, Object>>) result.get("photos");
        if (photoRefs != null) {
            for (Map<String, Object> photo : photoRefs) {
                photos.add((String) photo.get("photo_reference"));
            }
        }

        // 오픈 시간 처리
        List<String> openingHours = new ArrayList<>();
        Map<String, Object> openingHoursData = (Map<String, Object>) result.get("opening_hours");
        if (openingHoursData != null) {
            List<String> weekdayText = (List<String>) openingHoursData.get("weekday_text");
            if (weekdayText != null) {
                openingHours.addAll(weekdayText);
            }
        }

        // 리뷰 처리
        List<String> reviews = new ArrayList<>();
        List<Map<String, Object>> reviewList = (List<Map<String, Object>>) result.get("reviews");
        if (reviewList != null) {
            int reviewCount = Math.min(reviewList.size(), 20);  // 최대 20개 리뷰
            for (int i = 0; i < reviewCount; i++) {
                Map<String, Object> review = reviewList.get(i);
                reviews.add((String) review.get("text"));
            }
        }

        return PlaceDetailDto.builder()
                .placeId(placeId)
                .name((String) result.get("name"))
                .address((String) result.get("formatted_address"))
                .photos(photos)
                .priceLevel(result.containsKey("price_level") ? (String) result.get("price_level") : "가격정보 없음")
                .rating((Double) result.get("rating"))
                .openingHours(openingHours)
                .website((String) result.get("website"))
                .phoneNumber((String) result.get("formatted_phone_number"))
                .reviews(reviews)
                .build();
    }
}