package com.travel.security.auth.dto;

import com.travel.domain.member.entity.Member;
import com.travel.domain.member.entity.MemberRole;
import com.travel.domain.member.entity.Provider;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserInfo implements UserInfo {

    private String nickname;
    private String email;
    private String password;

    @Override
    public Member toEntity() {
        return Member.builder()
                .provider(Provider.CUSTOM)
                .nickname(getNickname())
                .email(getEmail())
                .password(getPassword())
                .role(MemberRole.USER)
                .build();
    }

    @Override
    public String getProviderId() {
        return null;
    }

    @Override
    public String getEmail() {
        return email;
    }
}
