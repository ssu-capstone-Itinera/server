package com.travel.domain.placetype.entity.tourattraction;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SubjectiveTag {
    한적한("한적한"),
    활발한("활발한"),
    낭만적인("낭만적인"),
    모험적인("모험적인"),
    힐링("힐링"),
    인스타감성("인스타감성"),
    여유로운("여유로운"),
    이국적인("이국적인"),
    전통적인("전통적인");
    private final String value;
}
