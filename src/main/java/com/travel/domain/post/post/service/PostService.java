package com.travel.domain.post.post.service;

import com.travel.domain.member.entity.Member;
import com.travel.domain.post.post.dao.PostLikeRepository;
import com.travel.domain.post.post.dao.PostRepository;
import com.travel.domain.post.post.domain.Post;
import com.travel.domain.post.post.dto.response.PostResponse;
import com.travel.domain.trip.dto.response.TripResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;


    public List<PostResponse> getPostList(Member member) {

        List<Post> postList = postRepository.findByMember(member);
        return postList.stream()
                .map(PostResponse::of)
                .collect(Collectors.toList());
    }
}
