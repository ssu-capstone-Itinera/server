package com.travel.domain.place.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.persistence.Id;

import org.springframework.data.elasticsearch.annotations.*;

import com.travel.domain.categories.entity.Category;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "place")
//@Setting(settingPath = "elasticsearch/settings.json")
//@Mapping(mappingPath = "elasticsearch/mappings.json")
public class PlaceDocument {
    @Id private Long id;

    @Field(type = FieldType.Keyword)
    private String placeType;

    @Field(type = FieldType.Keyword)
    private Category category;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String location;

    @Field(type = FieldType.Double)
    private Double rating;

    @Field(type = FieldType.Text)
    private String description;


    @Field(type = FieldType.Object)
    private Map<String, Object> details;

    @Field(type = FieldType.Date)
    private LocalDateTime createdAt;

    @Field(type = FieldType.Date)
    private LocalDateTime updatedAt;

}
