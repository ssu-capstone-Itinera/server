package com.travel.domain.member.service;

import com.travel.domain.follow.service.FollowService;
import com.travel.domain.member.dto.response.ProfileResponse;
import com.travel.domain.post.post.service.PostLikeService;
import com.travel.domain.post.post.service.PostService;
import com.travel.domain.trip.service.TripService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.domain.member.dao.MemberRepository;
import com.travel.domain.member.dto.MemberDto;
import com.travel.domain.member.entity.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final FollowService followService;
    private final PostService postService;
    private final PostLikeService postLikeService;
    private final TripService tripService;

    @Transactional(readOnly = true)
    public MemberDto getMemberProfile(Long memberId) {
        Member member = memberRepository.findByIdOrElseThrow(memberId);

        return MemberDto.of(member);
    }


    public ProfileResponse getMyPage(Long memberId) {
        Member member = memberRepository.findByIdOrElseThrow(memberId);


        return ProfileResponse.builder()
                .memberId(member.getId())
                .nickName(member.getNickName())
                .profileImage(member.getProfileImage())
                .email(member.getEmail())
                .followerCounts(followService.getFollowerCounts(member))
                .follwingCounts(followService.getFollowingCounts(member))
                .tripList(tripService.getTripList(member))
                .postResponseList(postService.getPostList(member))
                .postLikeList(postLikeService.getPostLikeList(member))
                .build();
    }
}
