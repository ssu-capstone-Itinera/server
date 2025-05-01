package com.travel.domain.post.service;

import com.travel.domain.member.entity.Member;
import com.travel.domain.post.post.dao.PostLikeRepository;
import com.travel.domain.post.post.dao.PostRepository;
import com.travel.domain.post.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;


    public List<Post> getPostList(Member member) {
        return postRepository.findByMember(member);
    }
}
