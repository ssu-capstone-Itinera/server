package com.travel.domain.post.post.entity;

import java.util.ArrayList;
import java.util.List;

import com.travel.domain.member.entity.Member;
import jakarta.persistence.*;

import com.travel.domain.post.comment.domain.PostComment;
import com.travel.domain.trip.entity.Trip;
import com.travel.global.common.entity.BaseTimeEntity;

import lombok.*;

@Entity
@Table(name = "post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "content", length = 1000)
    private String content;

    @OneToMany(mappedBy = "post")
    private List<PostComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<PostLike> likes = new ArrayList<>();

    @Builder
    public Post(Member member, Trip trip, String title, String content){
        this.member = member;
        this.trip = trip;
        this.title = title;
        this.content = content;
    }
    /*
    public Review(Member member, Book book, double rating, String content, Privacy privacy){
        this.member = member;
        this.book = book;
        this.content = content;
        this.privacy = privacy;
        setRatingFromDouble(rating);
    }
     */

    public void likePost(PostLike postLike) {
        this.likes.add(postLike);
    }

    public void unlikePost(PostLike postLike){
        this.likes.remove(postLike);
    }
}
