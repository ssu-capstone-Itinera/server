package com.travel.domain.placetype.entity.tourattraction;

import com.travel.domain.place.entity.PlaceDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "tourattraction")
//@Setting(settingPath = "elasticsearch/settings.json")
//@Mapping(mappingPath = "elasticsearch/mappings.json")
public class TourattractionDoc extends PlaceDocument {
    @Field(type = FieldType.Keyword)
    private String attractionType;


    // API 검색 가능 태그 (정적 태그)
    @Field(type = FieldType.Nested)
    private List<ApiTag> apiTags = new ArrayList<>();

    // 주관적 태그 (LLM 기반 분류)
    @Field(type = FieldType.Nested)
    private List<SubjectiveTag> subjectiveTags = new ArrayList<>();

    // 키워드 충돌 방지를 위한 필터링 설정
    @Field(type = FieldType.Object)
    private Map<String, Object> searchFilters;
}
