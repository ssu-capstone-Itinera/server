package com.travel.domain.post.comment.service;

import com.travel.domain.post.comment.dao.PostCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostCommentService {
    private final PostCommentRepository postCommentRepository;
//    public List<PostCommentResponse> getPostCommentResponseList(Post post) {
//        List<PostComment> postCommentList = postCommentRepository.findByPost(post);
//    }
}
