package com.travel.domain.itinerary.dao;

import com.travel.domain.itinerary.entity.Itinerary;
import com.travel.domain.trip.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {

    List<Itinerary> findByTrip(Trip trip);

    Itinerary findByTripAndTourDate(Trip trip, LocalDate tourDate);

    List<Itinerary> findByTripId(Long tripId);


}