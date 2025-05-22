package com.travel.domain.post.comment.dto.response;

import com.travel.domain.member.dto.MemberDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostCommentResponse {
    private MemberDto memberDto;
    private String content;
    private Long postLikes;
}
