package com.travel.domain.post.post.dto.response;

import com.travel.domain.post.post.domain.Post;
import com.travel.domain.post.post.domain.PostLike;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PostLikeResponse {
    private Long postId;

    public static PostLikeResponse of(PostLike postLike) {
        return PostLikeResponse.builder()
                .postId(postLike.getPost().getId())
                .build();
    }
}
