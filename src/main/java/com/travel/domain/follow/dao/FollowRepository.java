package com.travel.domain.follow.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.domain.follow.entity.Follow;
import com.travel.domain.member.entity.Member;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findFollowingByFollower(Member follwer);

    List<Follow> findFollowersByFollowing(Member following);

    boolean existsByFollowerAndFollowing(Member member, Member followMember);

    void deleteByFollowerAndFollowing(Member member, Member followMember);
}
