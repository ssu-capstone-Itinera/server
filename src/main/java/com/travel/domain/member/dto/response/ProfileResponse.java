package com.travel.domain.member.dto.response;

import com.travel.domain.itinerary.entity.Itinerary;
import com.travel.domain.post.post.domain.Post;
import com.travel.domain.post.post.domain.PostLike;
import com.travel.domain.trip.entity.Trip;
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
    private List<Trip> tripList;
    private List<Post> postList;
    private List<PostLike> postLikeList;

}
