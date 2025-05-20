package com.travel.domain.post.post.dao;

import com.travel.domain.member.entity.Member;
import com.travel.domain.post.post.domain.Post;
import com.travel.domain.post.post.domain.PostLike;
import com.travel.global.common.error.CustomException;
import com.travel.global.common.error.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    Optional<PostLike> findById(Long postLikeId);
    default PostLike findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new CustomException(ErrorCode.POSTLIKE_NOT_FOUND));
    }


    List<PostLike> findByMember(Member member);

    Optional<PostLike> findByMemberAndPost(Member member, Post post);
}
