package com.travel.global.security;

import com.travel.domain.member.domain.MemberRole;
import com.travel.global.util.CookieUtil;
import com.travel.security.auth.dto.token.AccessTokenDto;
import com.travel.security.auth.dto.token.RefreshTokenDto;
import com.travel.security.auth.service.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.util.Optional;

import static com.travel.global.common.constants.SecurityConstants.*;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenService jwtTokenService;
    private final CookieUtil cookieUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String accessTokenHeaderValue = extractAccessTokenFromHeader(request);
        String accessTokenValue = extractAccessTokenFromCookie(request);
        String refreshTokenValue = extractRefreshTokenFromCookie(request);

        if (accessTokenHeaderValue != null) {
            AccessTokenDto accessTokenDto =
                    jwtTokenService.retrieveOrReissueAccessToken(accessTokenHeaderValue);
            if (accessTokenDto != null) {
                setAuthenticationToContext(accessTokenDto.memberId(), accessTokenDto.memberRole());
                filterChain.doFilter(request, response);
                return;
            }
        }

        if (accessTokenValue == null || refreshTokenValue == null) {
            filterChain.doFilter(request, response);
            return;
        }


        AccessTokenDto accessTokenDto =
                jwtTokenService.retrieveOrReissueAccessToken(accessTokenValue);

        if (accessTokenDto != null) {
            setAuthenticationToContext(
                    accessTokenDto.memberId(), accessTokenDto.memberRole());
            filterChain.doFilter(request, response);
            return;
        }

        // AT가 만료된 경우 AT 재발급, 만료되지 않은 경우 null 반환
        Optional<AccessTokenDto> reissuedAccessToken =
                Optional.ofNullable(jwtTokenService.retrieveOrReissueAccessToken(accessTokenValue));
        // RT 유효하면 파싱, 유효하지 않으면 null 반환
        RefreshTokenDto refreshTokenDto = jwtTokenService.retrieveRefreshToken(refreshTokenValue);


        // AT가 만료되었고, RT가 유효하면 AT, RT 재발급
        if (refreshTokenDto != null) {
            String accessToken =
                    jwtTokenService.createAccessToken(
                            refreshTokenDto.memberId(), MemberRole.USER);
            String refreshToken = jwtTokenService.createRefreshToken(refreshTokenDto.memberId());

            HttpHeaders httpHeaders = cookieUtil.generateTokenCookies(accessToken, refreshToken);
            response.addHeader(
                    HttpHeaders.SET_COOKIE, httpHeaders.getFirst(ACCESS_TOKEN_COOKIE_NAME));
            response.addHeader(
                    HttpHeaders.SET_COOKIE, httpHeaders.getFirst(REFRESH_TOKEN_COOKIE_NAME));

            setAuthenticationToContext(refreshTokenDto.memberId(), MemberRole.USER);
        }

        filterChain.doFilter(request, response);
    }


    private void setAuthenticationToContext(Long memberId, MemberRole memberRole) {
//        UserDetails userDetails = new PrincipalDetails(memberId, memberRole.toString());
//        Authentication authentication =
//                new UsernamePasswordAuthenticationToken(
//                        userDetails, null, userDetails.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails =
                User.withUsername(memberId.toString())
                        .authorities(memberRole.toString())
                        .password("")
                        .build();
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);
    }


    private static String extractAccessTokenFromHeader(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(header -> header.startsWith(TOKEN_PREFIX))
                .map(header -> header.replace(TOKEN_PREFIX, ""))
                .orElse(null);
    }

    private String extractAccessTokenFromCookie(HttpServletRequest request) {
        return Optional.ofNullable(WebUtils.getCookie(request, ACCESS_TOKEN_COOKIE_NAME))
                .map(Cookie::getValue)
                .orElse(null);
    }

    private String extractRefreshTokenFromCookie(HttpServletRequest request) {
        return Optional.ofNullable(WebUtils.getCookie(request, REFRESH_TOKEN_COOKIE_NAME))
                .map(Cookie::getValue)
                .orElse(null);
    }
}