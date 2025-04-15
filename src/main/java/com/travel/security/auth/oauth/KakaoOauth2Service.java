package com.travel.security.auth.oauth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.travel.global.common.error.CustomException;
import com.travel.global.common.error.ErrorCode;
import com.travel.security.auth.dto.KakaoUserInfo;
import com.travel.security.auth.dto.response.KakaoTokenResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoOauth2Service implements Oauth2Service {

    @Value("${security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.registration.kakao.client-secret}")
    private String clientSecret;

    @Value("${security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    @Value("${security.oauth2.client.provider.kakao.token-uri}")
    private String tokenUri;

    @Value("${security.oauth2.client.registration.kakao.authorization-grant-type}")
    private String grantType;

    private static final String KAKAO_USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";

    @Override
    public KakaoUserInfo getUserInfo(String code) {
        KakaoTokenResponse tokenResponse = getAccessToken(code);
        return getUserInfoWithToken(tokenResponse.getAccess_token());
    }

    private KakaoTokenResponse getAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 요청 파라미터 설정
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        // HTTP 요청 엔티티 생성
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        try {
            // API 호출
            ResponseEntity<KakaoTokenResponse> responseEntity =
                    restTemplate.exchange(
                            tokenUri, HttpMethod.POST, requestEntity, KakaoTokenResponse.class);

            // 응답 결과 반환
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                log.info("카카오 토큰 발급 성공");
                return responseEntity.getBody();
            } else {
                log.error("카카오 토큰 발급 실패: {}", responseEntity.getStatusCode());
                throw new RuntimeException("카카오 토큰 발급에 실패했습니다.");
            }
        } catch (Exception e) {
            log.error("카카오 토큰 요청 중 오류 발생", e);
            throw new RuntimeException("카카오 토큰 요청 중 오류가 발생했습니다.", e);
        }
    }

    private KakaoUserInfo getUserInfoWithToken(String accessToken) {
        JSONObject response =
                WebClient.create()
                        .get()
                        .uri(KAKAO_USER_INFO_URI)
                        .headers(httpHeaders -> httpHeaders.setBearerAuth(accessToken))
                        .retrieve()
                        .bodyToMono(JSONObject.class)
                        .block();

        if (response == null) {
            throw new CustomException(ErrorCode.KAKAO_USER_INFO_FAILED);
        }

        KakaoUserInfo userInfo = new KakaoUserInfo();
        userInfo.setId(response.get("id").toString());
        userInfo.setKakao_account((Map<String, Object>) response.get("kakao_account"));

        return userInfo;
    }
}
