package com.travel.domain.post.post.dao;

import com.travel.domain.member.entity.Member;
import com.travel.domain.post.post.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    Optional<PostLike> findById(Long postLikeId);

    List<PostLike> findByMember(Member member);
}
