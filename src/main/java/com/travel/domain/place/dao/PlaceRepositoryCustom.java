package com.travel.domain.place.dao;

import com.travel.domain.place.entity.Place;

import java.util.List;

public interface PlaceRepositoryCustom {
    public List<Place> findByIdInOrderByRatingDescWithCursor(
            List<Long> placeIds,
            Double ratingCursor,
            Long idCursor,
            int pageSize
    );
}
