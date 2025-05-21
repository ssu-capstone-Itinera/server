package com.travel.domain.datapipeline.google.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.*;

@Getter
@Setter
public class PlaceListDto {
    private List<PlaceDto> results = new ArrayList<>();
    private String nextPageToken;
}
