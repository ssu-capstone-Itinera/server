package com.travel.domain.post.post.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.travel.domain.post.post.dto.request.PostRequest;
import com.travel.domain.post.post.dto.response.PostDetailResponse;
import com.travel.domain.post.post.dto.response.PostLikeResponse;
import com.travel.domain.post.post.dto.response.PostResponse;
import com.travel.domain.post.post.entity.Post;
import com.travel.domain.post.post.service.PostService;
import com.travel.global.common.response.CursorPageResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/post")
@Tag(name = "Post", description = "게시물 API")
public class PostController {
    private final PostService postService;

    @Operation(summary = "게시글 업로드 api")
    @PostMapping
    public ResponseEntity<Post> uploadPost(
            @AuthenticationPrincipal Long memberId, @RequestBody PostRequest postRequest) {

        return ResponseEntity.ok(postService.uploadPost(memberId, postRequest));
    }

    @Operation(summary = "게시글 상세 조회 api")
    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponse> getPostDetail(
            @AuthenticationPrincipal Long memberId, @PathVariable Long postId) {

        return ResponseEntity.ok(postService.getPostDetail(memberId, postId));
    }

    @Operation(summary = "게시글 좋아요 api - api 요청 보낼 때마다 좋아요,취소 반복")
    @PostMapping("/like/{postId}")
    public ResponseEntity<PostLikeResponse> likePost(
            @AuthenticationPrincipal Long memberId, @PathVariable Long postId) {

        return ResponseEntity.ok(postService.likePost(postId, memberId));
    }

    @Operation(summary = "게시글 List 조회(최신순)")
    @GetMapping
    public ResponseEntity<CursorPageResponse<PostResponse>> getPostList(
            @AuthenticationPrincipal Long memberId) {

        return ResponseEntity.ok(postService.getPostList(memberId));
    }
}
