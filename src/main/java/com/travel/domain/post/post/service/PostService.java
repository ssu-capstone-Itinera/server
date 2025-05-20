package com.travel.domain.post.post.service;

import com.travel.domain.member.dao.MemberRepository;
import com.travel.domain.member.dto.MemberDto;
import com.travel.domain.member.entity.Member;
import com.travel.domain.post.comment.domain.PostComment;
import com.travel.domain.post.post.dao.PostLikeRepository;
import com.travel.domain.post.post.dao.PostRepository;
import com.travel.domain.post.post.domain.Post;
import com.travel.domain.post.post.domain.PostLike;
import com.travel.domain.post.post.dto.request.PostRequest;
import com.travel.domain.post.post.dto.response.PostLikeResponse;
import com.travel.domain.post.post.dto.response.PostDetailResponse;
import com.travel.domain.post.post.dto.response.UserPostLikeResponse;
import com.travel.domain.post.post.dto.response.UserPostListResponse;
import com.travel.domain.trip.dto.response.TripResponse;
import com.travel.domain.trip.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostLikeRepository postLikeRepository;
    private final TripService tripService;


    @Transactional
    public List<UserPostListResponse> getUserPostList(Member member) {

        List<Post> postList = postRepository.findByMember(member);
        return postList.stream()
                .map(UserPostListResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public Post uploadPost(Long memberId, PostRequest postRequest) {
        Member member = memberRepository.findByIdOrElseThrow(memberId);
        Post post = Post.builder()
                .member(member)
                .trip(postRequest.getTrip())
                .title(postRequest.getTitle())
                .content(postRequest.getTitle())
                .build();

        return postRepository.save(post);
    }

    @Transactional
    public PostDetailResponse getPostDetail(Long memberId, Long postId) {
        Member member = memberRepository.findByIdOrElseThrow(memberId);
        Post post = postRepository.findByIdOrElseThrow(postId);
        MemberDto memberDto = MemberDto.of(member);
        TripResponse tripResponse = tripService.getTrip(post.getTrip());
        List<PostComment> postCommentList = post.getComments();
        Long countLikes = (long)post.getLikes().size();

        return PostDetailResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .memberDto(memberDto)
                .tripResponse(tripResponse)
                .postCommentList(postCommentList)
                .countLikes(countLikes)
                .build();
    }


    @Transactional
    public PostLikeResponse likePost(Long postId, Long memberId) {
        Member member = memberRepository.findByIdOrElseThrow(memberId);
        Post post = postRepository.findByIdOrElseThrow(postId);
        boolean isLiked = true;
        Optional<PostLike> optionalPostLike = postLikeRepository.findByMemberAndPost(member, post);
        if(optionalPostLike.isPresent()){
            PostLike postLike = optionalPostLike.get();
            post.unlikePost(postLike);
            postLikeRepository.delete(postLike);
            isLiked = false;
        }else{
            PostLike postlike = PostLike.builder()
                    .member(member)
                    .post(post)
                    .build();
            postLikeRepository.save(postlike);
            post.likePost(postlike);
            isLiked = true;
        }

        return PostLikeResponse.builder()
                .memberId(memberId)
                .postId(postId)
                .isLiked(isLiked)
                .build();
    }


    //사용자가 좋아요 누른 postId의 리스트
    @Transactional
    public List<UserPostLikeResponse> getUserPostLikeList(Member member) {
        List<PostLike> postLikes = postLikeRepository.findByMember(member);

        return postLikes.stream()
                .map(UserPostLikeResponse::of)
                .collect(Collectors.toList());
    }
}
