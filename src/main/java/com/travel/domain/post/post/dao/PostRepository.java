package com.travel.domain.post.post.dao;

import com.travel.domain.member.entity.Member;
import com.travel.domain.post.post.entity.Post;
import com.travel.global.common.error.CustomException;
import com.travel.global.common.error.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom{

    Optional<Post> findById(Long post);

    List<Post> findByMember(Member member);

    default Post findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
    }

}
