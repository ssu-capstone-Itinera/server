package com.travel.domain.place.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyPlaceRequest {
    @Schema(description = "검색 종류입니다. myPlace_keyword/myPlace_address")
    private String searchType;

    @Schema(description = "keyword 검색 시 사용할 검색어", nullable = true)
    private String myPlaceQuery;

    @Schema(description = "address 검색 시 사용할 검색어", nullable = true)
    private String myPlaceAddress;
}