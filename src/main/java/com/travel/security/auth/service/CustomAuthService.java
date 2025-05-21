package com.travel.security.auth.service;

import com.travel.security.auth.dto.CustomUserInfo;
import com.travel.security.auth.dto.KakaoUserInfo;
import com.travel.security.auth.dto.request.SignupRequest;
import com.travel.security.auth.dto.response.KakaoTokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomAuthService{
    private final PasswordEncoder passwordEncoder;

    public CustomUserInfo getUserInfo(SignupRequest signupRequest) {
        CustomUserInfo userInfo = new CustomUserInfo();
        userInfo.setNickname(signupRequest.getUserName());
        userInfo.setEmail(signupRequest.getEmail());
        userInfo.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        return userInfo;
    }
}
