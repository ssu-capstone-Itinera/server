package com.travel.domain.place.dto;

import com.travel.domain.place.entity.Place;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyPlaceSelectRequest {

    @Schema(description = "사용자가 작성한 myPlace의 이름입니다")
    private String customName;

    @Schema(description = "사용자가 선택한 장소입니다")
    private Place myPlace;
}
