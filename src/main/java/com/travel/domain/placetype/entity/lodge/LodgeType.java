package com.travel.domain.placetype.entity.lodge;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LodgeType {
    NONE("선택 안 함"),
    HOTEL("호텔"),
    MOTEL("모텔"),
    RESORT("리조트"),
    PENSION("펜션"),
    GUESTHOUSE("게스트하우스"),
    HOSTEL("호스텔");
    private final String value;
}
