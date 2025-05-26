package com.travel.domain.place.dao;

import com.travel.domain.member.entity.Member;
import com.travel.domain.place.entity.Place;
import com.travel.global.common.error.CustomException;
import com.travel.global.common.error.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long>, PlaceRepositoryCustom{
    Optional<Place> findByPlaceGoogleId(String findByPlaceGoogleId);

    boolean existsByPlaceGoogleId(String placeId);

    default Place findByPlaceGoogleIdOrElseThrow(String id) {
        return findByPlaceGoogleId(id).orElseThrow(() -> new CustomException(ErrorCode.PLACE_NOT_FOUND));
    }
}
