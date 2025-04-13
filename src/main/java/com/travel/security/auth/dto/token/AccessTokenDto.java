package com.travel.security.auth.dto.token;

import com.travel.domain.member.entity.MemberRole;

public record AccessTokenDto(Long memberId, MemberRole memberRole, String token) {}