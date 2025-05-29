package com.travel.domain.trip.service;

import com.travel.domain.itinerary.dto.ItinerarySaveRequest;
import com.travel.domain.itinerary.dto.SimplePlaceDto;
import com.travel.domain.itinerary.service.ItineraryService;
import com.travel.domain.member.dao.MemberRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.travel.domain.trip.dto.response.SimpleTripResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import com.travel.domain.member.entity.Member;
import com.travel.domain.trip.dao.TripRepository;
import com.travel.domain.trip.dto.request.TripSaveRequest;
import com.travel.domain.trip.dto.response.TripGetResponse;
import com.travel.domain.trip.dto.response.TripResponse;
import com.travel.domain.trip.dto.response.TripSaveResponse;
import com.travel.domain.trip.entity.Trip;

import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final MemberRepository memberRepository;
    private final ItineraryService itineraryService;

    public TripSaveResponse saveTrip(TripSaveRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));

        Trip trip = Trip.builder()
                .member(member)
                .title(request.getTitle())
                .mainTourPlace(request.getMainTourPlaces())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .isPublic(request.getIsPublic())
                .build();
        tripRepository.save(trip);

        for (int i = 0; i < request.getListOfPlaces().size(); i++) {
            List<SimplePlaceDto> dailyPlaceList = request.getListOfPlaces().get(i);

            ItinerarySaveRequest itinerarySaveRequest = new ItinerarySaveRequest();
            itinerarySaveRequest.setTripId(trip.getId());
            itinerarySaveRequest.setMemberId(member.getId());
            itinerarySaveRequest.setTourDate(request.getStartDate().plusDays(i));
            itinerarySaveRequest.setPlaces(dailyPlaceList);
            itinerarySaveRequest.setDailyTourPlace(request.getMainTourPlaces().get(i));

            itineraryService.saveItinerary(itinerarySaveRequest);
            log.info("itinerary {} save request send", request.getStartDate().plusDays(i));
        }

        return TripSaveResponse.builder()
                .tripId(trip.getId())
                .title(trip.getTitle())
                .mainTourPlace(trip.getMainTourPlace())
                .startDate(trip.getStartDate())
                .endDate(trip.getEndDate())
                .isPublic(trip.getIsPublic())
                .listOfPlaces(request.getListOfPlaces())
                .build();
    }

    public TripGetResponse getTripGetResponse(Long tripId, Long memberId) {
        Trip trip = getTripByIdAndMemberId(tripId, memberId);
        List<List<SimplePlaceDto>> listOfPlaces = itineraryService.getListOfPlaceByTripIdAndMemberId(tripId, memberId);
        return TripGetResponse.builder()
                .tripId(trip.getId())
                .title(trip.getTitle())
                .startDate(trip.getStartDate())
                .endDate(trip.getEndDate())
                .isPublic(trip.getIsPublic())
                .mainTourPlace(trip.getMainTourPlace())
                .listOfPlaces(listOfPlaces)
                .build();
    }

    private Trip getTripByIdAndMemberId(Long tripId, Long memberId) {
        return tripRepository.findByIdAndMemberId(tripId, memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원의 Trip이 존재하지 않습니다."));
    }

    public List<TripResponse> getTripList(Member member) {
        List<Trip> tripList = tripRepository.findByMember(member);
        return tripList.stream().map(TripResponse::of).collect(Collectors.toList());
    }

    public TripResponse getTripResponse(Trip trip) {

        return TripResponse.builder()
                .tripId(trip.getId())
                .title(trip.getTitle())
                .mainTourPlace(trip.getMainTourPlace())
                .startDate(trip.getStartDate())
                .startDate(trip.getEndDate())
                .build();
    }

    public Trip getTripById(Long tripId) {

        return tripRepository.findByIdOrElseThrow(tripId);
    }

    public List<SimpleTripResponse> getSimpleTripListByMemberId(Long memberId) {
        List<Trip> tripList = tripRepository.findByMemberId(memberId);
        List<SimpleTripResponse> simpleTripResponseList= new ArrayList<>();
        for(Trip trip : tripList) {
            simpleTripResponseList.add(SimpleTripResponse.builder()
                    .tripId(trip.getId())
                    .title(trip.getTitle())
                    .startDate(trip.getStartDate())
                    .endDate(trip.getEndDate())
                    .build());
        }
        return simpleTripResponseList;
    }
}
