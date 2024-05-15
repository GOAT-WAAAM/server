package com.goat.server.auth.dto.response;

import com.goat.server.global.domain.type.AccessToken;
import lombok.Builder;

@Builder
public record SignUpSuccessResponse(
        String accessToken,
        String refreshToken
) {
    public static SignUpSuccessResponse From(AccessToken token) {
        return SignUpSuccessResponse.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();
    }
}
