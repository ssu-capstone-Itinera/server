package com.travel.security.auth.dto;

import com.travel.domain.member.entity.Member;

public interface UserInfo {
    String getProviderId();
    Member toEntity();
}
