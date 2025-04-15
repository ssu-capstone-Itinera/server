package com.travel.domain.member.entity;

import jakarta.persistence.Column;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "refreshToken")
public class RefreshToken {

    @Id private Long memberId;

    @Column(name = "refresh_token")
    private String refreshToken;

    @TimeToLive private long ttl;

    @Builder
    public RefreshToken(Long memberId, String token, long ttl) {
        this.memberId = memberId;
        this.refreshToken = token;
        this.ttl = ttl;
    }

    @Builder
    public RefreshToken(Long memberId, String refreshToken) {
        this.memberId = memberId;
        this.refreshToken = refreshToken;
    }

    public RefreshToken updateRefreshToken(String newRefreshToken) {
        this.refreshToken = newRefreshToken;
        return this;
    }
}
