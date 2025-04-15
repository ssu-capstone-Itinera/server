package com.travel.infra.config.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.travel.infra.config.jwt.JwtProperties;

@EnableConfigurationProperties({JwtProperties.class})
@Configuration
public class PropertiesConfig {}
