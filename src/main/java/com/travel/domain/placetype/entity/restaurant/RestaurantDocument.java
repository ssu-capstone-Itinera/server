package com.travel.domain.placetype.entity.restaurant;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.*;
import com.travel.domain.place.entity.PlaceDocument;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "restaurant")
@Setting(settingPath = "elasticsearch/settings.json")
@Mapping(mappingPath = "elasticsearch/mappings.json")
public class RestaurantDocument extends PlaceDocument {
    @Field(type = FieldType.Keyword)
    private RestaurantType restaurantType;

    @Field(type = FieldType.Keyword)
    private RestaurantPriceRange restaurantPriceRange;

    @Field(type = FieldType.Object)
    private Map<String, Object> searchFilters;

}
