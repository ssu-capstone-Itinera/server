package com.travel.domain.follow.dao;

import com.travel.domain.follow.entity.Follow;
import com.travel.domain.member.entity.Member;
import com.travel.global.common.error.CustomException;
import com.travel.global.common.error.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findFollowingByFollower(Member follwer);
    List<Follow> findFollowersByFollowing(Member following);

    boolean existsByFollowerAndFollowing(Member member, Member followMember);

    void deleteByFollowerAndFollowing(Member member, Member followMember);
}
