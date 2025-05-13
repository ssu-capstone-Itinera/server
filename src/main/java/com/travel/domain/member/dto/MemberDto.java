package com.travel.domain.member.dto;

import com.travel.domain.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDto {
    private Long id;

    private String nickName;

    private String profileImage;

    private String email;

    public static MemberDto of(Member member) {
        return new MemberDto(
                member.getId(), member.getNickName(), member.getProfileImage(), member.getEmail());
    }
}
