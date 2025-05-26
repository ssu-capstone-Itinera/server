package com.travel.domain.placetype.dao.tourattraction;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.travel.domain.placetype.entity.tourattraction.SubjectiveTag;
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

    /*
       @Field(type = FieldType.Nested)
    private List<TourattractionTag> apiTags;

    @Field(type = FieldType.Text)
    private String address;

    // 주관적 태그 (LLM 기반 분류)
    @Field(type = FieldType.Nested)
    private List<SubjectiveTag> subjectiveTags;
     */

    @Override
    public List<String> findPlaceGoogleIdsByAddressAndTourattractionTags(
            String address, TourattractionTag tourattractionTag) {
        try {
            // Elasticsearch 쿼리 빌드
            SearchRequest searchRequest =
                    SearchRequest.of(
                            s ->
                                    s.index("tourattraction") // 인덱스 이름 (실제 인덱스명으로 변경 필요)
                                            .query(
                                                    q ->
                                                            q.bool(
                                                                    b ->
                                                                            b.must(
                                                                                            m ->
                                                                                                    m
                                                                                                            .match(
                                                                                                                    ma ->
                                                                                                                            ma.field(
                                                                                                                                            "address") // 주소 필드명
                                                                                                                                    .query(
                                                                                                                                            address)))
                                                                                    .must(
                                                                                            m ->
                                                                                                    m
                                                                                                            .term(
                                                                                                                    t ->
                                                                                                                            t.field(
                                                                                                                                            "tourattractionTags") // 레스토랑 타입 필드명
                                                                                                                                    .value(
                                                                                                                                            tourattractionTag
                                                                                                                                                    .name())))))
                                            .source(
                                                    so ->
                                                            so.filter(
                                                                    f ->
                                                                            f.includes(
                                                                                    "googlePlaceId") // Google Place ID 필드만 조회
                                                                    ))
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

    @Override
    public List<String> findPlaceGoogleIdsByAddressAndSubjectiveTags(
            String address, SubjectiveTag subjectiveTag) {
        try {
            // Elasticsearch 쿼리 빌드
            SearchRequest searchRequest =
                    SearchRequest.of(
                            s ->
                                    s.index("places") // 인덱스 이름 (실제 인덱스명으로 변경 필요)
                                            .query(
                                                    q ->
                                                            q.bool(
                                                                    b ->
                                                                            b.must(
                                                                                            m ->
                                                                                                    m
                                                                                                            .match(
                                                                                                                    ma ->
                                                                                                                            ma.field(
                                                                                                                                            "address") // 주소 필드명
                                                                                                                                    .query(
                                                                                                                                            address)))
                                                                                    .must(
                                                                                            m ->
                                                                                                    m
                                                                                                            .term(
                                                                                                                    t ->
                                                                                                                            t.field(
                                                                                                                                            "subjectiveTags") // 레스토랑 타입 필드명
                                                                                                                                    .value(
                                                                                                                                            subjectiveTag
                                                                                                                                                    .name())))))
                                            .source(
                                                    so ->
                                                            so.filter(
                                                                    f ->
                                                                            f.includes(
                                                                                    "googlePlaceId") // Google Place ID 필드만 조회
                                                                    ))
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
