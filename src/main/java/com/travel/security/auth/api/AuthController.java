package com.travel.security.auth.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.travel.security.auth.dto.request.RegisterRequest;
import com.travel.security.auth.dto.response.AuthResponse;
import com.travel.security.auth.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "카카오 로그인")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> loginByKakao(@RequestBody RegisterRequest registerRequest) {
        return new ResponseEntity<>(authService.signIn(registerRequest), HttpStatus.OK);
    }

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping("/withdraw")
    public void withdraw(@AuthenticationPrincipal Long memberId) {
        authService.withdraw(memberId);
    }
}
