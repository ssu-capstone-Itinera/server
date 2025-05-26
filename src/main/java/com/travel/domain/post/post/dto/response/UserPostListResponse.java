package com.travel.domain.post.post.dto.response;

import com.travel.domain.post.post.entity.Post;
import com.travel.domain.trip.entity.Trip;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserPostListResponse {
    private Long postId;
    private Trip trip;

    private String title;

    private String content;

    public static UserPostListResponse of(Post post) {
        return UserPostListResponse.builder()
                .postId(post.getId())
                .trip(post.getTrip())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }
}
