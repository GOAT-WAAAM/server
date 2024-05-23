package com.goat.server.global.util;

import com.goat.server.global.domain.type.JwtProperties;
import com.goat.server.global.domain.type.Tokens;
import com.goat.server.global.domain.type.JwtValidationType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    private SecretKey secretKey;

    @PostConstruct
    public void setKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT 토큰 생성
    public Tokens generateToken(Authentication authentication) {
        return new Tokens(createToken(authentication, jwtProperties.getTokenExpirationTime()),
                createToken(authentication, jwtProperties.getRefreshTokenExpirationTime()));
    }

    private String createToken(Authentication authentication, Long expirationTime) {
        final Date now = new Date();

        final Claims claims = Jwts.claims()
                .setSubject(authentication.getPrincipal().toString())
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationTime));  // 만료 시간 설정

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // Header
                .setClaims(claims) // Claim
                .signWith(secretKey) // Signature
                .compact();
    }


    private SecretKey getSigningKey(String stringKey) {
        return Keys.hmacShaKeyFor(stringKey.getBytes());
    }

    public JwtValidationType validateToken(String token) {
        try {
            final Claims claims = getBody(token);
            return JwtValidationType.VALID_JWT;
        } catch (MalformedJwtException ex) {
            return JwtValidationType.INVALID_JWT_TOKEN;
        } catch (ExpiredJwtException ex) {
            return JwtValidationType.EXPIRED_JWT_TOKEN;
        } catch (UnsupportedJwtException ex) {
            return JwtValidationType.UNSUPPORTED_JWT_TOKEN;
        } catch (IllegalArgumentException ex) {
            return JwtValidationType.EMPTY_JWT;
        }
    }

    private Claims getBody(String token) {
        if (token.contains("Bearer ")) {
            token = token.substring("Bearer ".length());
        }

        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(token))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Long getUserIdFromJwt(String token) {
        Claims claims = getBody(token);
        return Long.valueOf(claims.getSubject());
    }

}
