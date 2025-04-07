package com.travel.security.auth.api;

import com.travel.security.auth.dto.request.RegisterRequest;
import com.travel.security.auth.dto.response.AuthResponse;
import com.travel.security.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @Operation(
            summary = "소셜 로그인")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> loginByKakao(
            @RequestBody RegisterRequest registerRequest) {
        return new ResponseEntity<>(authService.signIn(registerRequest), HttpStatus.OK);
    }

    @DeleteMapping("/withdraw")
    public void withdraw() {
        authService.withdraw();
    }

}
