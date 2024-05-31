package com.goat.server.auth.dto.response;

import com.goat.server.global.util.Tokens;
import lombok.Builder;

@Builder
public record SignUpSuccessResponse(
        String accessToken,
        String refreshToken
) {
    public static SignUpSuccessResponse from(Tokens token) {
        return SignUpSuccessResponse.builder()
                .accessToken(token.accessToken())
                .refreshToken(token.refreshToken())
                .build();
    }
}
