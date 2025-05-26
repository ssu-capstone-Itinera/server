package com.travel.domain.member.dto.response;

import java.util.List;

import com.travel.domain.post.post.dto.response.UserPostLikeResponse;
import com.travel.domain.post.post.dto.response.UserPostListResponse;
import com.travel.domain.trip.dto.response.TripResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
    private List<UserPostListResponse> userPostListResponseList;
    private List<UserPostLikeResponse> postLikeList;
}
