package com.travel.domain.place.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.domain.place.entity.Place;
import com.travel.global.common.error.CustomException;
import com.travel.global.common.error.ErrorCode;

public interface PlaceRepository extends JpaRepository<Place, Long>, PlaceRepositoryCustom {
    Optional<Place> findByPlaceGoogleId(String findByPlaceGoogleId);

    boolean existsByPlaceGoogleId(String placeId);

    default Place findByPlaceGoogleIdOrElseThrow(String id) {
        return findByPlaceGoogleId(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PLACE_NOT_FOUND));
    }
}
