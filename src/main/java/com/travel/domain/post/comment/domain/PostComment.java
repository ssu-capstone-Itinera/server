package com.travel.domain.post.comment.domain;

import com.travel.domain.member.domain.Member;
import com.travel.domain.post.post.domain.Post;
import com.travel.domain.post.post.domain.PostLike;
import com.travel.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "content", length = 1000)
    private String content;

    @OneToMany(mappedBy = "comment")
    private List<PostCommentLike> postCommentLikes = new ArrayList<>();
}