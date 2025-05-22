package com.travel.domain.post.comment.service;

import com.travel.domain.post.comment.dao.PostCommentRepository;
import com.travel.domain.post.comment.domain.PostComment;
import com.travel.domain.post.comment.dto.response.PostCommentResponse;
import com.travel.domain.post.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostCommentService {
    private final PostCommentRepository postCommentRepository;
//    public List<PostCommentResponse> getPostCommentResponseList(Post post) {
//        List<PostComment> postCommentList = postCommentRepository.findByPost(post);
//    }
}
