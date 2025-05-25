package com.travel.domain.itinerary.service;


import com.travel.domain.categories.entity.Category;
import com.travel.domain.itinerary.dao.ItineraryRepository;
import com.travel.domain.itinerary.dto.ItinerarySaveRequest;
import com.travel.domain.itinerary.dto.ItinerarySaveResponse;
import com.travel.domain.itinerary.dto.SimplePlaceDto;
import com.travel.domain.itinerary.entity.Itinerary;
import com.travel.domain.member.dao.MemberRepository;
import com.travel.domain.member.entity.Member;
import com.travel.domain.place.dao.MyPlaceRepository;
import com.travel.domain.place.dao.PlaceRepository;
import com.travel.domain.place.entity.MyPlace;
import com.travel.domain.place.entity.Place;
import com.travel.domain.trip.dao.TripRepository;
import com.travel.domain.trip.entity.Trip;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItineraryService {
    private final TripRepository tripRepository;
    private final MemberRepository memberRepository;
    private final PlaceRepository placeRepository;
    private final ItineraryRepository itineraryRepository;
    private final MyPlaceRepository myPlaceRepository;

    public ItinerarySaveResponse saveItinerary(ItinerarySaveRequest itinerarySaveRequest) {
        //Trip을 저장할 때, Trip을 먼저 저장을 하고 Itinerary를 저장하는 과정을 반복해야합니다(중요)
        Trip trip = tripRepository.findById(itinerarySaveRequest.getTripId())
                .orElseThrow(() -> new IllegalArgumentException("ID에 해당하는 Trip이 없습니다: " + itinerarySaveRequest.getTripId()));
        Member member = memberRepository.findById(itinerarySaveRequest.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("ID에 해당하는 멤버가 없습니다: " + itinerarySaveRequest.getMemberId()));

        List<Place> places = new ArrayList<>();
        List<MyPlace> myPlaces =  new ArrayList<>();
        List<String> itineraryPlaceTypeOrder = new ArrayList<>();

        resolveSimplePlaces(itinerarySaveRequest.getPlaces(), member, places, myPlaces, itineraryPlaceTypeOrder);

        Itinerary itinerary = Itinerary.builder()
                .trip(trip)
                .member(member)
                .tourDate(itinerarySaveRequest.getTourDate())
                .places(places)
                .myPlaces(myPlaces)
                .itineraryPlaceTypeOrder(itineraryPlaceTypeOrder)
                .build();

        itineraryRepository.save(itinerary);

        return ItinerarySaveResponse.builder()
                .itineraryId(itinerary.getId())
                .tourDate(itinerary.getTourDate())
                .places(itinerarySaveRequest.getPlaces())
                .build();
    }

    private void resolveSimplePlaces(
            List<SimplePlaceDto> simplePlaceDtoList,
            Member member,
            List<Place> places,
            List<MyPlace> myPlaces,
            List<String> itineraryPlaceTypeOrder){
        for(SimplePlaceDto simplePlace: simplePlaceDtoList){
            if(Category.MY_PLACE.equals(simplePlace.getCategory())){
                MyPlace myPlace = myPlaceRepository.findByMemberAndPlaceGoogleId(member, simplePlace.getPlaceGoogleId())
                        .orElseGet(() -> {
                            MyPlace myPlace_ = MyPlace.builder()
                                    .placeGoogleId(simplePlace.getPlaceGoogleId())
                                    .name(simplePlace.getName())
                                    .lat(simplePlace.getLat())
                                    .lng(simplePlace.getLng())
                                    .member(member)
                                    .build();
                            return myPlaceRepository.save(myPlace_);
                        });
                myPlaces.add(myPlace);
                itineraryPlaceTypeOrder.add("M");
            }else{
                Place place = placeRepository.findById(simplePlace.getPlaceId().longValue())
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 placeId: " + simplePlace.getPlaceId()));
                places.add(place);
                itineraryPlaceTypeOrder.add("P");
            }
        }
    }
}