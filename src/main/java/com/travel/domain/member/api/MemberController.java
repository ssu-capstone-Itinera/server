package com.travel.domain.member.api;

import com.travel.domain.member.dto.MemberDto;
import com.travel.domain.member.dto.response.ProfileResponse;
import com.travel.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/member")
@Tag(name = "Member", description = "맴버 API")
public class MemberController {
    private final MemberService memberService;

//    @Operation(summary = "멤버 정보 조회")
//    @GetMapping("/{member}/profileInfo")
//    public ResponseEntity<ProfileResponse> getProfileInformation() {
//        return ResponseEntity.ok(memberService.getProfile(memberId, cursorId));
//    }
//
//    @Operation(summary = "맴버 프로필페이지 정보")
//    @GetMapping("/{memberId}/profileInfo")
//    public ResponseEntity<ProfileResponse> getProfileInformation(@PathVariable Long memberId,
//                                                                 @RequestParam(value = "cursorId", required = false) Long cursorId) {
//        return ResponseEntity.ok(memberService.getProfile(memberId, cursorId));
//    }

    @Operation(summary = "맴버 최소 정보 조회")
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDto> getMemberProfile(@PathVariable Long memberId) {
        return ResponseEntity.ok(memberService.getMemberProfile(memberId));
    }

}
