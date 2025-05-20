package com.travel.domain.post.post.api;

import com.travel.domain.post.post.domain.Post;
import com.travel.domain.post.post.dto.request.PostRequest;
import com.travel.domain.post.post.dto.response.PostLikeResponse;
import com.travel.domain.post.post.dto.response.PostDetailResponse;
import com.travel.domain.post.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/post")
@Tag(name = "Post", description = "게시물 API")
public class PostController {
    private final PostService postService;
    @Operation(summary = "게시글 업로드 api")
    @PostMapping
    public ResponseEntity<Post> uploadPost(@AuthenticationPrincipal Long memberId, @RequestBody PostRequest postRequest){

        return ResponseEntity.ok(postService.uploadPost(memberId, postRequest));
    }

    @Operation(summary = "게시글 상세 조회 api")
    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponse> getPostDetail(@AuthenticationPrincipal Long memberId, @PathVariable Long postId){

        return ResponseEntity.ok(postService.getPostDetail(memberId, postId));
    }

    @Operation(summary = "게시글 좋아요 api")
    @PostMapping("/like/{postId}")
    public ResponseEntity<PostLikeResponse> likePost(@AuthenticationPrincipal Long memberId, @PathVariable Long postId) {

        return ResponseEntity.ok(postService.likePost(postId, memberId));
    }

}
