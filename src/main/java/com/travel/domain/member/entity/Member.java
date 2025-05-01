package com.travel.domain.member.entity;

import java.util.ArrayList;
import java.util.List;

import com.travel.domain.post.comment.domain.PostComment;
import com.travel.domain.post.comment.domain.PostCommentLike;
import com.travel.domain.post.post.domain.Post;
import com.travel.domain.post.post.domain.PostLike;
import com.travel.domain.trip.entity.Trip;
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
