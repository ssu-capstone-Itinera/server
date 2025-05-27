package com.travel.domain.placetype.dao.tourattraction;

import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.travel.domain.placetype.entity.tourattraction.TourattractionDoc;

@Repository
public interface TourattractionElasticsearchRepository
        extends ElasticsearchRepository<TourattractionDoc, String>, TourattractionRepositoryCustom {
    Optional<TourattractionDoc> findByPlaceGoogleId(String placeGoogleId);
}
