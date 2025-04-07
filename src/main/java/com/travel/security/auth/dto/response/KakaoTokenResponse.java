package com.travel.security.auth.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 카카오 토큰 정보를 담는 응답 객체
 */
@Getter
@Setter
@NoArgsConstructor
public class KakaoTokenResponse {
    private String token_type;
    private String access_token;
    private int expires_in;
    private String refresh_token;
    private int refresh_token_expires_in;

    /** 인증된 사용자의 정보 조회 권한 범위 */
    private String scope;
}

