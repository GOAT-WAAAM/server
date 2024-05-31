package com.goat.server.global.config;

import com.goat.server.global.domain.type.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtPropertiesConfig {
}
