package com.travel.domain.post.comment.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.domain.post.comment.domain.PostComment;
import com.travel.global.common.error.CustomException;
import com.travel.global.common.error.ErrorCode;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

    Optional<PostComment> findById(Long aLong);

    default PostComment findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new CustomException(ErrorCode.POSTCOMMENT_NOT_FOUNT));
    }

    /*
    default PostLike findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new CustomException(ErrorCode.POSTLIKE_NOT_FOUND));
    }
     */
}
