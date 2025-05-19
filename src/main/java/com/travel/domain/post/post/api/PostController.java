package com.travel.domain.post.post.api;

import com.travel.domain.post.post.dto.request.PostRequest;
import com.travel.domain.post.post.dto.response.PostResponse;
import com.travel.domain.post.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/post")
@Tag(name = "Post", description = "게시물 API")
public class PostController {
//    private final PostService postService;
//    @Operation(summary = "게시글 업로드 api")
//    @PostMapping
//    public ResponseEntity<PostResponse> uploadPost(@AuthenticationPrincipal Long memberId, @RequestBody PostRequest postRequest){
//
//        return postService.uploadPost(memberId, postRequest);
//    }
}
