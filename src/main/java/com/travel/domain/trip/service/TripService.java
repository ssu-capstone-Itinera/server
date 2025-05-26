package com.travel.domain.trip.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.travel.domain.member.entity.Member;
import com.travel.domain.trip.dao.TripRepository;
import com.travel.domain.trip.dto.response.TripResponse;
import com.travel.domain.trip.entity.Trip;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TripService {
    private final TripRepository tripRepository;

    public List<TripResponse> getTripList(Member member) {
        List<Trip> tripList = tripRepository.findByMember(member);
        return tripList.stream().map(TripResponse::of).collect(Collectors.toList());
    }

    public TripResponse getTrip(Trip trip) {

        return TripResponse.builder()
                .tripId(trip.getId())
                .title(trip.getTitle())
                .regin(trip.getRegin())
                .startDate(trip.getStartDate())
                .startDate(trip.getEndDate())
                .budget(trip.getBudget())
                .build();
    }
}
