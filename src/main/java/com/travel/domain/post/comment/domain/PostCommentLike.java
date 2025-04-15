package com.travel.domain.post.comment.domain;

import jakarta.persistence.*;

import com.travel.domain.member.entity.Member;
import com.travel.global.common.entity.BaseTimeEntity;

import lombok.*;

@Getter
@Setter
@Table(name = "post_comment_like")
@Entity
@NoArgsConstructor
public class PostCommentLike extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_comment_like_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostComment comment;

    @Builder
    public PostCommentLike(Member member, PostComment comment) {
        this.member = member;
        this.comment = comment;
    }
}
