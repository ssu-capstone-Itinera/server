package com.travel.domain.place.dao;

import com.travel.domain.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long>, PlaceRepositoryCustom{
    Optional<Place> findByPlaceGoogleId(String findByPlaceGoogleId);

    boolean existsGoogleId(String placeId);
}
