package com.travel.domain.placetype.dao.restaurant;

import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.travel.domain.placetype.entity.restaurant.RestaurantDoc;

@Repository
public interface RestaurantElasticsearchRepository
        extends ElasticsearchRepository<RestaurantDoc, String>, RestaurantRepositoryCustom {
    Optional<RestaurantDoc> findByPlaceGoogleId(String placeGoogleId);
}
