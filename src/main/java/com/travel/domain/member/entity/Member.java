package com.travel.domain.member.entity;

import com.travel.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member")
@Getter
@Setter
@NoArgsConstructor
public class Member extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String nickName;

    @Column
    private String email;


    @Column
    private String profileImage;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    private Provider provider;
    @Column
    private String providerId;

    @Builder
    public Member(String nickName, String email, String profileImage, MemberRole role,
                  Provider provider, String providerId) {
        this.nickName = nickName;
        this.email = email;
        this.profileImage = profileImage;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }

}
