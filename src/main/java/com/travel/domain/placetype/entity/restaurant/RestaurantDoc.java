package com.travel.domain.placetype.entity.restaurant;

import java.util.Map;

import jakarta.persistence.Id;
import lombok.experimental.SuperBuilder;
import org.springframework.data.elasticsearch.annotations.*;

import com.travel.domain.place.entity.PlaceDocument;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "restaurant")
@Setting(settingPath = "elasticsearch/settings.json")
@Mapping(mappingPath = "elasticsearch/mappings.json")
public class RestaurantDoc extends PlaceDocument {
    @Field(type = FieldType.Keyword)
    private RestaurantType restaurantType;

    @Field(type = FieldType.Text)
    private String address;

//    @Field(type = FieldType.Keyword)
//    private RestaurantPriceRange restaurantPriceRange;

    @Field(type = FieldType.Object)
    private Map<String, Object> searchFilters;
}
