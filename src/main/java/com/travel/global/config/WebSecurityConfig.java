package com.travel.global.config;
import static org.springframework.security.config.Customizer.withDefaults;

import com.travel.global.security.JwtAuthenticationFilter;
import com.travel.global.util.CookieUtil;
import com.travel.global.util.JwtUtil;
import com.travel.security.auth.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtTokenService jwtTokenService;
    private final CookieUtil cookieUtil;

    private void defaultFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        defaultFilterChain(http);

        http.authorizeHttpRequests(
                authorize ->
                        authorize
                                //swagger
                                .requestMatchers(
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html"
                                ).permitAll()
                                .requestMatchers("/api/itinera/**")
                                .permitAll()
                                .requestMatchers("/api/v1/auth/register\"")
                                .authenticated() // 소셜 로그인 임시 토큰으로 인증
                                .requestMatchers("/api/vi/auth/**")
                                .permitAll() // 임시 회원가입 / 로그인 + OAuth2 로그인
                                .requestMatchers("/api/v1/**")
                                .permitAll() // 임시로 모든 요청 허용
                                .anyRequest()
                                .authenticated());

        http.exceptionHandling(
                exception ->
                        exception.authenticationEntryPoint(
                                (request, response, authException) -> response.setStatus(401)));

        http.addFilterBefore(
                jwtAuthenticationFilter(jwtTokenService),
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.singletonList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(
            JwtTokenService jwtTokenService) {
        return new JwtAuthenticationFilter(jwtTokenService);
    }
}
