package com.travel.domain.datapipeline.google.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReviewDto {
    private String authorName;
    private String authorUrl;
    private String profilePhotoUrl;
    private Double rating;
    private String text;
    private String relativeTimeDescription;
    private String language;
}
