package com.travel.global.util;


import com.travel.domain.member.entity.MemberRole;
import com.travel.infra.config.jwt.JwtProperties;
import com.travel.security.auth.dto.token.AccessTokenDto;
import com.travel.security.auth.dto.token.RefreshTokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import static com.travel.global.common.constants.SecurityConstants.TOKEN_ROLE_NAME;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final JwtProperties jwtProperties;



    public String generateAccessToken(Long memberId, MemberRole memberRole) {
        Date issuedAt = new Date();
        Date expiredAt =
                new Date(issuedAt.getTime() + jwtProperties.getAccessTokenExpiration());
        return buildAccessToken(memberId, memberRole, issuedAt, expiredAt);
    }



    public String generateRefreshToken(Long memberId) {
        Date issuedAt = new Date();
        Date expiredAt =
                new Date(issuedAt.getTime() + jwtProperties.getRefreshTokenExpiration());
        return buildRefreshToken(memberId, issuedAt, expiredAt);
    }

    public AccessTokenDto generateAccessTokenDto(Long memberId, MemberRole memberRole) {
        Date issuedAt = new Date();
        Date expiredAt =
                new Date(issuedAt.getTime() + jwtProperties.accessTokenExpirationMilliTime());
        String tokenValue = buildAccessToken(memberId, memberRole, issuedAt, expiredAt);
        return new AccessTokenDto(memberId, memberRole, tokenValue);
    }

    public RefreshTokenDto generateRefreshTokenDto(Long memberId) {
        Date issuedAt = new Date();
        Date expiredAt =
                new Date(issuedAt.getTime() + jwtProperties.refreshTokenExpirationMilliTime());
        String tokenValue = buildRefreshToken(memberId, issuedAt, expiredAt);
        return new RefreshTokenDto(
                memberId, tokenValue, jwtProperties.getRefreshTokenExpiration());
    }

    private String buildAccessToken(
            Long memberId, MemberRole memberRole, Date issuedAt, Date expiredAt) {
        return Jwts.builder()
                .setIssuer(jwtProperties.getIssuer())
                .setSubject(memberId.toString())
                .claim(TOKEN_ROLE_NAME, memberRole.name())
                .setIssuedAt(issuedAt)
                .setExpiration(expiredAt)
                .signWith(getAccessTokenKey())
                .compact();
    }

    private String buildRefreshToken(Long memberId, Date issuedAt, Date expiredAt) {
        return Jwts.builder()
                .setIssuer(jwtProperties.getIssuer())
                .setSubject(memberId.toString())
                .setIssuedAt(issuedAt)
                .setExpiration(expiredAt)
                .signWith(getRefreshTokenKey())
                .compact();
    }


    private Key getRefreshTokenKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getRefreshTokenSecret().getBytes());
    }

    private Key getAccessTokenKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getAccessTokenSecret().getBytes());
    }

    public AccessTokenDto parseAccessToken(String token) throws ExpiredJwtException {
        try {
            Jws<Claims> claims = getClaims(token);

            return new AccessTokenDto(
                    Long.parseLong(claims.getBody().getSubject()),
                    MemberRole.valueOf(claims.getBody().get(TOKEN_ROLE_NAME, String.class)),
                    token);
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (Exception e) {
            return null;
        }
    }

    public RefreshTokenDto parseRefreshToken(String token) throws ExpiredJwtException {
        try {
            Jws<Claims> claims = getClaims(token);

            return new RefreshTokenDto(
                    Long.parseLong(claims.getBody().getSubject()),
                    token,
                    jwtProperties.getRefreshTokenExpiration());
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (Exception e) {
            return null;
        }
    }


    /*
    그냥 Claims로 반환하면 JWT에서 Payload(Claims) 부분만 추출
    Jws<Claims>를 반환하면 헤더(Header), 서명(Signature) 정보까지 포함한 전체 JWT 파싱 결과를 얻을 수 있음.
     */
    public Jws<Claims> getClaims(String token) {
        Key key = getAccessTokenKey();
        return Jwts.parserBuilder()
                .requireIssuer(jwtProperties.getIssuer())
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token); // 토큰 검증 만료되면 ExpiredJwtException 발생
    }

    public Date getTokenExpirationDate(String token, boolean isAccessToken) {
        try {
            Key key = isAccessToken ? getAccessTokenKey() : getRefreshTokenKey();
            Claims claims = Jwts.parserBuilder()
                    .requireIssuer(jwtProperties.getIssuer())
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getExpiration();
        } catch (Exception e) {
            log.error("Failed to get token expiration date", e);
            return null;
        }
    }


    public Long getRefreshTokenExpirationTime() {
        return jwtProperties.getRefreshTokenExpiration();
    }
}

