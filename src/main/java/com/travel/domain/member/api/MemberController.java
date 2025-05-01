package com.travel.domain.member.api;

import com.travel.domain.member.dto.response.ProfileResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travel.domain.member.dto.MemberDto;
import com.travel.domain.member.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/member")
@Tag(name = "Member", description = "멤버 API")
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "멤버 최소 정보 조회")
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDto> getMemberProfile(@PathVariable Long memberId) {
        return ResponseEntity.ok(memberService.getMemberProfile(memberId));
    }

    @Operation(summary = "마이페이지 정보 조회")
    @GetMapping("/mypage/{memberId}")
    public ResponseEntity<ProfileResponse> getMyPage(@PathVariable Long memberId) {
        return ResponseEntity.ok(memberService.getMyPage(memberId));
    }
}
