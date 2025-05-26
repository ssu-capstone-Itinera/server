package com.travel.domain.placetype.dao.cafe;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.travel.domain.placetype.entity.cafe.CafeTag;
import com.travel.domain.placetype.entity.restaurant.RestaurantType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class CafeRepositoryImpl implements CafeRepositoryCustom{

    private final ElasticsearchClient elasticsearchClient;
    @Override
    public List<String> findPlaceGoogleIdsByAddressAndCafeTags(String address, CafeTag cafeTag) {
        try {
            // Elasticsearch 쿼리 빌드
            SearchRequest searchRequest = SearchRequest.of(s -> s
                    .index("cafe") // 인덱스 이름 (실제 인덱스명으로 변경 필요)
                    .query(q -> q
                            .bool(b -> b
                                    .must(m -> m
                                            .matchPhrase(ma -> ma
                                                    .field("address")
                                                    .query(address)
                                            )
                                    )
                                    .must(m -> m
                                            .term(t -> t
                                                    .field("cafeTags") // 레스토랑 타입 필드명
                                                    .value(cafeTag.name())
                                            )
                                    )
                            )
                    )
                    .source(so -> so
                            .filter(f -> f
                                    .includes("googlePlaceId") // Google Place ID 필드만 조회
                            )
                    )
                    .size(100) // 최대 결과 수 (필요에 따라 조정)
            );

            // 검색 실행
            SearchResponse<Map> response = elasticsearchClient.search(searchRequest, Map.class);

            // 결과에서 Google Place ID 추출
            return response.hits().hits().stream()
                    .map(hit -> hit.source())
                    .filter(Objects::nonNull)
                    .map(source -> (String) source.get("googlePlaceId"))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            log.error("Elasticsearch 검색 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("장소 검색 중 오류가 발생했습니다.", e);
        }
    }

}

