package com.travel.domain.member.entity;

import jakarta.persistence.*;

import com.travel.global.common.entity.BaseTimeEntity;

import lombok.*;

@Entity
@Table(name = "member")
@Getter
@Setter
@NoArgsConstructor
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column String nickname;

    @Column private String email;

    @Column private String profileImage;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Provider provider;

    @Column private String providerId;

    @Builder
    public Member(
            String nickname,
            String email,
            String profileImage,
            MemberRole role,
            String password,
            Provider provider,
            String providerId) {
        this.nickname = nickname;
        this.email = email;
        this.profileImage = profileImage;
        this.password = password;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }
}
