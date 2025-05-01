package com.travel.domain.post.service;

import com.travel.domain.member.entity.Member;
import com.travel.domain.post.post.dao.PostLikeRepository;
import com.travel.domain.post.post.domain.PostLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    public List<PostLike> getPostLikeList(Member member) {
        return postLikeRepository.findByMember(member);
    }
}
