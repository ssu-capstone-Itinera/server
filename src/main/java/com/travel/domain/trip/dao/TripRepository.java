package com.travel.domain.trip.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.domain.member.entity.Member;
import com.travel.domain.trip.entity.Trip;
import com.travel.global.common.error.CustomException;
import com.travel.global.common.error.ErrorCode;

public interface TripRepository extends JpaRepository<Trip, Long> {

    Optional<Trip> findById(Long tripId);

    List<Trip> findByMember(Member member);

    default Trip findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new CustomException(ErrorCode.TRIP_NOT_FOUND));
    }
}
