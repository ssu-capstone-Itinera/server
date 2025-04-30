package com.travel.domain.place.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import jakarta.persistence.Id;

import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.elasticsearch.annotations.*;

import com.travel.domain.categories.entity.Category;

import lombok.*;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "place")
@Setting(settingPath = "elasticsearch/settings.json")
@Mapping(mappingPath = "elasticsearch/mappings.json")
public class PlaceDocument {

    @Id
    private Integer id;

    @Field(type = FieldType.Keyword)
    private String placeType;

    @Field(type = FieldType.Text)
    private String placeId;


    @Field(type = FieldType.Object)
    private Map<String, Object> details;

}
