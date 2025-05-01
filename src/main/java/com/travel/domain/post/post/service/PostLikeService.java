package com.travel.domain.post.post.service;

import com.travel.domain.member.entity.Member;
import com.travel.domain.post.post.dao.PostLikeRepository;
import com.travel.domain.post.post.domain.PostLike;
import com.travel.domain.post.post.dto.response.PostLikeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    public List<PostLikeResponse> getPostLikeList(Member member) {
         List<PostLike> postLikes = postLikeRepository.findByMember(member);

         return postLikes.stream()
                 .map(PostLikeResponse::of)
                 .collect(Collectors.toList());

    }
}
