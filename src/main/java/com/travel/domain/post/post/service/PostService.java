package com.travel.domain.post.post.service;

import com.travel.domain.member.entity.Member;
import com.travel.domain.member.service.MemberService;
import com.travel.domain.post.post.dao.PostLikeRepository;
import com.travel.domain.post.post.dao.PostRepository;
import com.travel.domain.post.post.domain.Post;
import com.travel.domain.post.post.dto.request.PostRequest;
import com.travel.domain.post.post.dto.response.PostResponse;
import com.travel.domain.post.post.dto.response.UserPostListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberService memberService;
    private final PostLikeRepository postLikeRepository;


    public List<UserPostListResponse> getUserPostList(Member member) {

        List<Post> postList = postRepository.findByMember(member);
        return postList.stream()
                .map(UserPostListResponse::of)
                .collect(Collectors.toList());
    }

//    public ResponseEntity<PostResponse> uploadPost(Long memberId, PostRequest postRequest) {
//        Member member = memberService.getMember(memberId);
//        Post post = Post.builder()
//                .member()
//                .build();
//    }
}
