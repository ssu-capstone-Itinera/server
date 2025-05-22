package com.travel.domain.placetype.entity.cafe;


import jakarta.persistence.Id;
import lombok.experimental.SuperBuilder;
import org.springframework.data.elasticsearch.annotations.*;
import com.travel.domain.place.entity.PlaceDocument;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Document(indexName = "cafe")
@Setting(settingPath = "elasticsearch/settings.json")
@Mapping(mappingPath = "elasticsearch/mappings.json")
public class CafeDoc extends PlaceDocument {

    @Field(type = FieldType.Keyword)
    private List<CafeTag> cafeTags;

}
