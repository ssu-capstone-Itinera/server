package com.travel.domain.place.entity;

import java.util.Map;

import com.travel.domain.categories.entity.Category;
import jakarta.persistence.Id;

import lombok.experimental.SuperBuilder;
import org.springframework.data.elasticsearch.annotations.*;

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
    private Category Category;

    @Field(type = FieldType.Keyword)
    private String placeType;

    @Field(type = FieldType.Text)
    private String placeGoogleId;

    @Field(type = FieldType.Text)
    private String placeName;

    @Field(type = FieldType.Text)
    private String address;

    @Field(type = FieldType.Double)
    private Double rating;




    @Field(type = FieldType.Object)
    private Map<String, Object> details;

}
