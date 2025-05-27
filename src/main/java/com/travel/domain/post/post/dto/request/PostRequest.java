package com.travel.domain.post.post.dto.request;

import com.travel.domain.trip.entity.Trip;

import lombok.Getter;

@Getter
public class PostRequest {
    private Long tripId;
    private String title;
    private String content;
}
