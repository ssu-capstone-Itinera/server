package com.travel.domain.post.post.dto.response;

import com.travel.domain.member.dto.MemberDto;
import com.travel.domain.trip.dto.response.TripResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostResponse {
    private MemberDto memberDto;
    private Long postId;
    private TripResponse tripResponse;
    private String title;
    private int countLikes;
    private int postComments;

}
