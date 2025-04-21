package com.travel.domain.placetype.dao.restaurant;

import com.travel.domain.placetype.entity.restaurant.RestaurantDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantElasticsearchRepository extends ElasticsearchRepository<RestaurantDocument, Long> {

}