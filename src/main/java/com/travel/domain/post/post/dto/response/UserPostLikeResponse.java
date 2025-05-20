package com.travel.domain.post.post.dto.response;

import com.travel.domain.member.entity.Member;
import com.travel.domain.post.post.domain.PostLike;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserPostLikeResponse {
    private Long postId;

    public static UserPostLikeResponse of(PostLike postLike) {
        return UserPostLikeResponse.builder()
                .postId(postLike.getPost().getId())
                .build();
    }
}
