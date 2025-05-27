package com.travel.domain.placetype.dao.tourattraction;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.travel.domain.placetype.entity.tourattraction.SubjectiveTag;
import com.travel.domain.placetype.entity.tourattraction.TourattractionDoc;
import com.travel.domain.placetype.entity.tourattraction.TourattractionTag;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TourattractionRepositoryImpl implements TourattractionRepositoryCustom {
    private final ElasticsearchClient elasticsearchClient;

    @Override
    public List<TourattractionDoc> findPlaceGoogleIdsByAddressAndTourattractionTags(
            String address, TourattractionTag tourattractionTag) {
        try {
            SearchRequest searchRequest = SearchRequest.of(s -> s
                    .index("tourattraction")
                    .query(q -> q
                            .bool(b -> b
                                    .must(m -> m
                                            .match(ma -> ma
                                                    .field("address")
                                                    .query(address)
                                            )
                                    )
                                    .must(m -> m
                                            .term(t -> t
                                                    .field("tourattractionTags")
                                                    .value(tourattractionTag.name())
                                            )
                                    )
                            )
                    )
                    .size(100)
            );

            SearchResponse<TourattractionDoc> response = elasticsearchClient.search(searchRequest, TourattractionDoc.class);

            return response.hits().hits().stream()
                    .map(hit -> hit.source())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            log.error("Elasticsearch 검색 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("장소 검색 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public List<TourattractionDoc> findPlaceGoogleIdsByAddressAndSubjectiveTags(
            String address, SubjectiveTag subjectiveTag) {
        try {
            SearchRequest searchRequest = SearchRequest.of(s -> s
                    .index("places")
                    .query(q -> q
                            .bool(b -> b
                                    .must(m -> m
                                            .match(ma -> ma
                                                    .field("address")
                                                    .query(address)
                                            )
                                    )
                                    .must(m -> m
                                            .term(t -> t
                                                    .field("subjectiveTags")
                                                    .value(subjectiveTag.name())
                                            )
                                    )
                            )
                    )
                    .size(100)
            );

            SearchResponse<TourattractionDoc> response = elasticsearchClient.search(searchRequest, TourattractionDoc.class);

            return response.hits().hits().stream()
                    .map(hit -> hit.source())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            log.error("Elasticsearch 검색 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("장소 검색 중 오류가 발생했습니다.", e);
        }
    }
}
