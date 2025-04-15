package com.travel.domain.member.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.travel.domain.member.entity.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMemberId(Long memberId);

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    void deleteByMemberId(Long memberId);

    void deleteByRefreshToken(String refreshToken);

    boolean existsByMemberId(Long memberId);
}
