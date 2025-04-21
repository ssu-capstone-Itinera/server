package com.travel.domain.placetype.dao.tourattraction;


import com.travel.domain.placetype.entity.tourattraction.TourattractionDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourattractionElasticsearchRepository extends ElasticsearchRepository<TourattractionDoc, Long> {
}
