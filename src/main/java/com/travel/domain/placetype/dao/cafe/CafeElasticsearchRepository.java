package com.travel.domain.placetype.dao.cafe;


import com.travel.domain.placetype.entity.cafe.CafeDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CafeElasticsearchRepository extends ElasticsearchRepository<CafeDoc, Long> {
}
