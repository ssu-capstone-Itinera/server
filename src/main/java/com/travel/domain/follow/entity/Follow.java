package com.travel.domain.follow.entity;

import jakarta.persistence.*;

import com.travel.domain.member.entity.Member;
import com.travel.global.common.entity.BaseTimeEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "follow")
@Getter
@Setter
@AllArgsConstructor
public class Follow extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower")
    private Member follower;

    @ManyToOne
    @JoinColumn(name = "following")
    private Member following;

    public Follow(Member follower, Member following){
        this.follower = follower;
        this.following = following;
    }
}
