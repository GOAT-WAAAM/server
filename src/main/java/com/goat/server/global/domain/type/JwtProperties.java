package com.goat.server.global.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
@Getter
@AllArgsConstructor
public class JwtProperties {

    private String issuer;
    private String secretKey;
    private Long tokenExpirationTime;
    private Long refreshTokenExpirationTime;
}
