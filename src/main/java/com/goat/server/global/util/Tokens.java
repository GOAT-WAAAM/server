package com.goat.server.global.util;

import lombok.Builder;

@Builder
public record Tokens(String accessToken, String refreshToken) {
}
