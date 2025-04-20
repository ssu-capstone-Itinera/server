package com.travel.domain.member.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import com.travel.domain.itinerary.entity.Itinerary;
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

    @Column String nickName;

    @Column private String email;

    @Column private String profileImage;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    @OneToMany(mappedBy = "member")
    private List<Itinerary> itinerarys = new ArrayList<>();

    @Column private String providerId;

    @Builder
    public Member(
            String nickName,
            String email,
            String profileImage,
            MemberRole role,
            Provider provider,
            String providerId) {
        this.nickName = nickName;
        this.email = email;
        this.profileImage = profileImage;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }
}
