package com.travel.domain.post.post.dto.request;

import com.travel.domain.member.entity.Member;
import com.travel.domain.trip.entity.Trip;
import lombok.Getter;

@Getter
public class PostRequest {
    private Member member;
    private Trip trip;
    private String title;
    private String content;

}
