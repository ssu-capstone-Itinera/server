package com.travel.domain.member.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.domain.member.entity.Member;
import com.travel.global.common.error.CustomException;
import com.travel.global.common.error.ErrorCode;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(Long memberId);

    Optional<Member> findByEmail(String memberEmail);

    Optional<Member> findByProviderId(String providerId);

    default Member findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
