package com.travel.domain.post.post.dto.response;

import com.travel.domain.member.entity.Member;
import com.travel.domain.post.post.entity.PostLike;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostLikeResponse {
    private Long postId;
    private Long memberId;
    private boolean isLiked;

    public static PostLikeResponse of(PostLike postLike, Member member, boolean isLiked) {
        return PostLikeResponse.builder()
                .postId(postLike.getPost().getId())
                .memberId(member.getId())
                .isLiked(isLiked)
                .build();
    }
}
