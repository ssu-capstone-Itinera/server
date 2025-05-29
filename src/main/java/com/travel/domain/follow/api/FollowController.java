package com.travel.domain.follow.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.travel.domain.follow.dto.FollowResponse;
import com.travel.domain.follow.service.FollowService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/follow")
@Tag(name = "Follow", description = "팔로우/팔로잉 관리 기능 구현")
public class FollowController {
    private final FollowService followService;

    @Operation(summary = "팔로우 기능")
    @PostMapping("/{followMemberId}")
    public ResponseEntity<?> follow(
            @AuthenticationPrincipal Long memberId, @PathVariable Long followMemberId) {
        followService.follow(memberId, followMemberId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "팔로우 취소 기능")
    @DeleteMapping("/{followMemberId}")
    public ResponseEntity<?> unfollow(
            @AuthenticationPrincipal Long memberId, @PathVariable Long followMemberId) {
        followService.unfollow(memberId, followMemberId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "팔로워 삭제 기능")
    @DeleteMapping("/{followerID}/follower")
    public ResponseEntity<?> deleteFollower(
            @AuthenticationPrincipal Long memberId, @PathVariable Long followerID) {
        followService.deleteFollower(memberId, followerID);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "팔로잉 조회 기능")
    @GetMapping("/following/{memberId}")
    public ResponseEntity<List<FollowResponse>> getFollowing(@PathVariable Long memberId) {
        return ResponseEntity.ok(followService.getFollowing(memberId));
    }

    @Operation(summary = "팔로워 조회 기능")
    @GetMapping("/followers/{memberId}")
    public ResponseEntity<List<FollowResponse>> getFollower(@PathVariable Long memberId) {
        return ResponseEntity.ok(followService.getFollower(memberId));
    }
}
