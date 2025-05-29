package com.travel.domain.placetype.entity.tourattraction;

import java.util.List;
import java.util.Map;

import org.springframework.data.elasticsearch.annotations.*;

import com.travel.domain.place.entity.PlaceDocument;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "tourattraction")
@Setting(settingPath = "elasticsearch/settings.json")
@Mapping(mappingPath = "elasticsearch/mappings.json")
public class TourattractionDoc extends PlaceDocument {

    // API 검색 가능 태그 (정적 태그)
    @Field(type = FieldType.Keyword)
    private List<TourattractionTag> tourattractionTags;

    @Field(type = FieldType.Text)
    private String address;

    // 주관적 태그 (LLM 기반 분류)
    @Field(type = FieldType.Keyword)
    private List<SubjectiveTag> subjectiveTags;

    @Field(type = FieldType.Keyword)
    private String placeGoogleId;

    // 키워드 충돌 방지를 위한 필터링 설정
    @Field(type = FieldType.Object)
    private Map<String, Object> searchFilters;
}
