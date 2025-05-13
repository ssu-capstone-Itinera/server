package com.travel.domain.placetype.dao.restaurant;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.travel.domain.placetype.entity.restaurant.RestaurantDoc;

import java.util.Optional;

@Repository
public interface RestaurantElasticsearchRepository
        extends ElasticsearchRepository<RestaurantDoc, Long> {
    Optional<RestaurantDoc> findByPlaceGoogleId(String placeGoogleId);
}
