package com.travel.domain.trip.dao;

import com.travel.domain.member.entity.Member;
import com.travel.domain.trip.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TripRepository extends JpaRepository<Trip, Long> {

    Optional<Trip> findById(Long tripId);

    List<Trip> findByMember(Member member);
}
