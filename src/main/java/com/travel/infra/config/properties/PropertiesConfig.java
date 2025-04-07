package com.travel.infra.config.properties;

import com.travel.infra.config.jwt.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({
        JwtProperties.class

})
@Configuration
public class PropertiesConfig {}