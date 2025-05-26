package com.travel.domain.placetype.dao.cafe;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import com.travel.domain.placetype.entity.cafe.CafeDoc;

import java.util.Optional;

@Repository
public interface CafeElasticsearchRepository extends ElasticsearchRepository<CafeDoc, Long>, CafeRepositoryCustom {

    Optional<CafeDoc> findByPlaceGoogleId(String placeGoogleId);
}
