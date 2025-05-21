package com.travel.global.security;

import static com.travel.global.common.constants.SecurityConstants.*;

import java.io.IOException;
import java.util.Optional;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import com.travel.domain.member.entity.MemberRole;
import com.travel.security.auth.dto.token.AccessTokenDto;
import com.travel.security.auth.service.JwtTokenService;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestPath = request.getServletPath();

        // Swagger 요청은 필터에서 제외
        if (requestPath.startsWith("/swagger-ui") || requestPath.startsWith("/v3/api-docs")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 아직 로그인 되지 않았을 때
        if (requestPath.contains("/api/v1/auth/register")) {
            filterChain.doFilter(request, response);
            return;
        }
        else if (requestPath.contains("/api/v1/auth/signup")) {
            filterChain.doFilter(request, response);
            return;
        }
        else if (requestPath.contains("/api/v1/auth/login")) {
            filterChain.doFilter(request, response);
            return;
        }
        else if (requestPath.contains("/api/v1/auth/refresh")) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = extractAccessTokenFromHeader(request);
        if (StringUtils.hasText(accessToken)) {
            try {
                AccessTokenDto accessTokenDto =
                        jwtTokenService.retrieveOrReissueAccessToken(accessToken);
                if (accessTokenDto != null) {
                    setAuthenticationToContext(
                            accessTokenDto.memberId(), accessTokenDto.memberRole());
                    filterChain.doFilter(request, response);
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Invalid Token");
                    return;
                }
            } catch (JwtException ex) {
                System.out.println("Invalid Token: " + ex.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or Expired JWT Token");
                return;
            }
        } else {
            log.warn("No Authorization token found in request header");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Authorization token is required");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthenticationToContext(Long memberId, MemberRole memberRole) {
        UsernamePasswordAuthenticationToken token =
                getUserAuthenticationToken(memberId, memberRole);
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    private static UsernamePasswordAuthenticationToken getUserAuthenticationToken(
            Long memberId, MemberRole memberRole) {
        UserDetails userDetails =
                User.withUsername(memberId.toString())
                        .authorities(memberRole.toString())
                        .password("")
                        .build();

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        memberId, null, userDetails.getAuthorities());
        return token;
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
