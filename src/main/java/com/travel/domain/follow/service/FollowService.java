package com.travel.domain.follow.service;

import com.travel.domain.follow.dao.FollowRepository;
import com.travel.domain.follow.dto.FollowResponse;
import com.travel.domain.follow.entity.Follow;
import com.travel.domain.member.dao.MemberRepository;
import com.travel.domain.member.entity.Member;
import com.travel.domain.member.service.MemberService;
import com.travel.global.common.error.CustomException;
import com.travel.global.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final MemberService memberService;

    @Transactional
    public void follow(Long memberId, Long followMemberId) {
        final Member member = memberService.getMember(memberId);
        final Member followMember = memberService.getMember(followMemberId);

        if (member.getId().equals(followMember.getId())) {
            throw new CustomException(ErrorCode.FOLLOW_MYSELF_FAILED);
        }
        if (!followRepository.existsByFollowerAndFollowing(member, followMember)) {
            Follow follow = new Follow(member, followMember);
            followRepository.save(follow);
        }else{
            throw new CustomException(ErrorCode.ALREADY_FOLLOWED);
        }
    }

    @Transactional
    public void unfollow(Long memberId, Long followMemberId) {
        if (memberId.equals(followMemberId)) {
            throw new CustomException(ErrorCode.FOLLOW_MYSELF_FAILED);
        }

        final Member member = memberService.getMember(memberId);
        final Member followMember = memberService.getMember(followMemberId);

        followRepository.deleteByFollowerAndFollowing(member, followMember);
    }

    @Transactional
    public void deleteFollower(Long memberId, Long followerID) {
        if (memberId.equals(followerID)) {
            throw new CustomException(ErrorCode.FOLLOW_MYSELF_FAILED);
        }
        final Member member = memberService.getMember(memberId);
        final Member followMember = memberService.getMember(followerID);
        followRepository.deleteByFollowerAndFollowing(followMember, member);
    }

    @Transactional(readOnly = true)
    public List<FollowResponse> getFollowing(Long memberId) {
        Member member = memberService.getMember(memberId);
        return followRepository.findFollowingByFollower(member)
                .stream()
                .map(follow -> FollowResponse.builder()
                        .memberId(follow.getFollowing().getId())
                        .nickname(follow.getFollowing().getNickName())
                        .profileImage(follow.getFollowing().getProfileImage())
                        .followDate(follow.getCreatedDate())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FollowResponse> getFollower(Long memberId) {
        Member member = memberService.getMember(memberId);
        return followRepository.findFollowersByFollowing(member)
                .stream()
                .map(follow -> FollowResponse.builder()
                        .memberId(follow.getFollower().getId())
                        .nickname(follow.getFollower().getNickName())
                        .profileImage(follow.getFollower().getProfileImage())
                        .followDate(follow.getCreatedDate())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Follow> getFollowWithFollowing(Long memberId) {
        Member member = memberService.getMember(memberId);
        return followRepository.findFollowingByFollower(member);
    }

    @Transactional(readOnly = true)
    public int getFollowingCounts(Long memberId) {
        Member member = memberService.getMember(memberId);
        return followRepository.findFollowersByFollowing(member).size();
    }

    @Transactional(readOnly = true)
    public int getFollowerCounts(Long memberId) {
        Member member = memberService.getMember(memberId);
        return followRepository.findFollowingByFollower(member).size();
    }
}
