package com.goat.server.global.util.jwt;

import lombok.Builder;

@Builder
public record Tokens(String accessToken, String refreshToken) {
}
