package com.travel.domain.trip.service;

import com.travel.domain.member.entity.Member;
import com.travel.domain.trip.dao.TripRepository;
import com.travel.domain.trip.entity.Trip;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService {
    private final TripRepository tripRepository;

    public List<Trip> getTripList(Member member) {
        return tripRepository.findByMember(member);
    }
}
