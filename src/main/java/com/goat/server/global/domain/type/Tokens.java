package com.goat.server.global.domain.type;

import lombok.Builder;

@Builder
public record Tokens(String accessToken, String refreshToken) {
}
