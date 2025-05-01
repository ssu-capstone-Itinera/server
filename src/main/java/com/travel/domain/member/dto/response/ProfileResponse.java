package com.travel.domain.member.dto.response;


import com.travel.domain.post.post.dto.response.PostLikeResponse;
import com.travel.domain.post.post.dto.response.PostResponse;
import com.travel.domain.trip.dto.response.TripResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ProfileResponse {
    private Long memberId;
    private String nickName;
    private String profileImage;
    private String email;
    private int follwingCounts;
    private int followerCounts;
    private List<TripResponse> tripList;
    private List<PostResponse> postResponseList;
    private List<PostLikeResponse> postLikeList;

}
