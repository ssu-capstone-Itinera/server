package com.travel.domain.post.post.dto.response;

import com.travel.domain.member.dto.MemberDto;
import com.travel.domain.post.comment.domain.PostComment;
import com.travel.domain.trip.dto.response.TripResponse;
import lombok.Builder;

import java.util.List;

@Builder
public class PostDetailResponse {
    private MemberDto memberDto;
    private TripResponse tripResponse;
    private String title;
    private String content;
    private Long countLikes;
    private List<PostComment> postCommentList;


}
