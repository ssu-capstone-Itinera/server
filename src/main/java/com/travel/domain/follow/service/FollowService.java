package com.travel.domain.follow.service;

import com.travel.domain.follow.dao.FollowRepository;
import com.travel.domain.follow.entity.Follow;
import com.travel.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;




    @Transactional(readOnly = true)
    public int getFollowerCounts(Member member) {
        return followRepository.findFollowersByFollowing(member).size();
    }

    @Transactional(readOnly = true)
    public int getFollowingCounts(Member member) {
        return followRepository.findFollowingByFollower(member).size();
    }

}
