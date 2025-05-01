package com.travel.domain.post.post.dto.response;

import com.travel.domain.post.post.domain.Post;
import com.travel.domain.trip.dto.response.TripResponse;
import com.travel.domain.trip.entity.Trip;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostResponse {
    private Trip trip;

    private String title;

    private String content;

    public static PostResponse of(Post post) {
        return PostResponse.builder()
                .trip(post.getTrip())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }
}
