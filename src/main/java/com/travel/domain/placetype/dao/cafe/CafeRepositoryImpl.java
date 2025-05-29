package com.travel.domain.placetype.dao.cafe;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.travel.domain.placetype.entity.cafe.CafeDoc;
import com.travel.domain.placetype.entity.cafe.CafeTag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class CafeRepositoryImpl implements CafeRepositoryCustom {

    private final ElasticsearchClient elasticsearchClient;

    @Override
    public List<CafeDoc> findCafesByAddressAndCafeTags(String address, CafeTag cafeTag) {
        try {
            SearchRequest searchRequest = SearchRequest.of(s -> s
                    .index("cafe")
                    .query(q -> q
                            .bool(b -> {
                                // 주소 조건 - 키워드가 포함되어 있으면 찾기
                                var builder = b.must(m -> m
                                        .match(ma -> ma
                                                .field("address")
                                                .query(address)
                                                .operator(co.elastic.clients.elasticsearch._types.query_dsl.Operator.And) // 모든 키워드 포함
                                        )
                                );

                                // cafeTag가 null이 아닌 경우에만 태그 조건 추가 - 정확히 같은 값 찾기
                                if (cafeTag != null) {
                                    builder.must(m -> m
                                            .term(t -> t
                                                    .field("cafeTags")
                                                    .value(cafeTag.name())
                                            )
                                    );
                                }

                                return builder;
                            })
                    )
                    .size(100) // 필요에 따라 조정
            );

            SearchResponse<CafeDoc> response = elasticsearchClient.search(searchRequest, CafeDoc.class);

            log.info("검색 결과: {} 건 (주소: {}, 태그: {})",
                    response.hits().total().value(), address, cafeTag);

            return response.hits().hits().stream()
                    .map(hit -> hit.source())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            log.error("Elasticsearch 검색 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("장소 검색 중 오류가 발생했습니다.", e);
        }
    }

    // 만약 Google ID만 필요한 경우를 위한 별도 메서드
    public List<String> findPlaceGoogleIdsByAddressAndCafeTags(String address, CafeTag cafeTag) {
        return findCafesByAddressAndCafeTags(address, cafeTag).stream()
                .map(CafeDoc::getPlaceGoogleId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}