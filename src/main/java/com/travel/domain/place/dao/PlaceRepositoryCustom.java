package com.travel.domain.place.dao;

import java.util.List;

import com.travel.domain.place.entity.Place;

public interface PlaceRepositoryCustom {
    public List<Place> findByPlaceGoogleIdInOrderByRatingDescWithCursor(
            List<String> placeGoogleIds, Double ratingCursor, Long idCursor, int pageSize);
}
