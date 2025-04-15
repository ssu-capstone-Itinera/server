package com.travel.security.auth.oauth;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Oauth2Factory {
    private static final String KAKAO_REGISTRATION_ID = "kakao";

    private final KakaoOauth2Service kakaoOauth2Service;

    public Oauth2Service of(String provider) {
        if (provider.equals(KAKAO_REGISTRATION_ID)) {
            return kakaoOauth2Service;
        }
        throw new OAuth2AuthenticationException("PROVIDER_NOT_SUPPORTED: " + provider);
    }
}
