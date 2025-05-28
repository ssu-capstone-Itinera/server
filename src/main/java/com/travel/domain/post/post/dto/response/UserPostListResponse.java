package com.travel.domain.post.post.dto.response;

import com.travel.domain.post.post.entity.Post;
import com.travel.domain.trip.dto.response.TripResponse;
import com.travel.domain.trip.entity.Trip;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserPostListResponse {
    private Long postId;
    private TripResponse tripResponse;

    private String title;

    private String content;

    public static UserPostListResponse of(Post post, TripResponse tripResponse) {
        return UserPostListResponse.builder()
                .postId(post.getId())
                .tripResponse(tripResponse)
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }
}
