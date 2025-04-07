package com.travel.infra.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String accessTokenSecret;
    private String refreshTokenSecret;
    private long accessTokenExpiration;
    private long refreshTokenExpiration;
    private String issuer = "kakao";

    public Long accessTokenExpirationMilliTime() {
        return accessTokenExpiration * 1000;
    }

    public Long refreshTokenExpirationMilliTime() {
        return refreshTokenExpiration * 1000;
    }
}
