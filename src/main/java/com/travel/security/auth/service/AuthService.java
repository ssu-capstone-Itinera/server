package com.travel.security.auth.service;

import com.travel.domain.member.dao.MemberRepository;
import com.travel.domain.member.dao.RefreshTokenRepository;
import com.travel.domain.member.domain.Member;
import com.travel.domain.member.domain.MemberRole;
import com.travel.domain.member.domain.RefreshToken;
import com.travel.global.common.error.CustomException;
import com.travel.global.common.error.ErrorCode;
import com.travel.global.util.JwtUtil;
import com.travel.security.auth.dto.UserInfo;
import com.travel.security.auth.dto.request.RegisterRequest;
import com.travel.security.auth.dto.response.AuthResponse;
import com.travel.security.auth.oauth.Oauth2Factory;
import com.travel.security.auth.oauth.Oauth2Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final Oauth2Factory oauth2Factory;


    @Transactional
    public AuthResponse signIn(RegisterRequest request) {
        Oauth2Service oAuth2Service = oauth2Factory.of(request.getProviderName());
        UserInfo userInfo = oAuth2Service.getUserInfo(request.getCode());
        Member member = findOrSignUp(userInfo);

        return generateResponse(member);
    }

    public AuthResponse generateResponse(Member member) {

        Long memberId = member.getId();
        MemberRole memberRole = member.getRole();

        // JwtUtil을 사용하여 토큰 생성
        String accessToken = jwtUtil.generateAccessToken(memberId, memberRole);
        String refreshToken = jwtUtil.generateRefreshToken(memberId);

        // 리프레시 토큰 저장 또는 업데이트
        refreshTokenRepository.findByMemberId(memberId)
                .ifPresentOrElse(
                        token -> {
                            token.updateRefreshToken(refreshToken);
                        },
                        () -> refreshTokenRepository.save(new RefreshToken(memberId, refreshToken))
                );

        // 토큰의 만료 시간 파싱
        Date accessTokenExpiration = jwtUtil.getTokenExpirationDate(accessToken, true);
        Date refreshTokenExpiration = jwtUtil.getTokenExpirationDate(refreshToken, false);

        // 응답 객체 생성
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiration(accessTokenExpiration)
                .refreshTokenExpiration(refreshTokenExpiration)
                .memberId(memberId)
                .nickName(member.getNickName())
                .profileImage(member.getProfileImage())
                .build();
    }

    private Member findOrSignUp(UserInfo userInfo) {
        return memberRepository.findByProviderId(userInfo.getProviderId())
                .orElseGet(() -> saveMember(userInfo));
    }

    private Member saveMember(UserInfo userInfo) {
        Member member = userInfo.toEntity();
        memberRepository.save(member);
        return memberRepository.save(member);
    }

    @Transactional
    public void withdraw(Long memberId) {
        memberRepository.deleteById(memberId);
        refreshTokenRepository.deleteByMemberId(memberId);
    }
}
