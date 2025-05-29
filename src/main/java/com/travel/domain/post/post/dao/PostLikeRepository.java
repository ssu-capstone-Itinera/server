package com.travel.domain.post.post.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.domain.member.entity.Member;
import com.travel.domain.post.post.entity.Post;
import com.travel.domain.post.post.entity.PostLike;
import com.travel.global.common.error.CustomException;
import com.travel.global.common.error.ErrorCode;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    Optional<PostLike> findById(Long postLikeId);

    default PostLike findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new CustomException(ErrorCode.POSTLIKE_NOT_FOUND));
    }

    List<PostLike> findByMember(Member member);

    Optional<PostLike> findByMemberAndPost(Member member, Post post);
}
