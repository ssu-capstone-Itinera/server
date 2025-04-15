package com.travel.security.auth.dto.token;

public record RefreshTokenDto(Long memberId, String token, Long ttl) {}
