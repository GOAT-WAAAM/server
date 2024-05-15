package com.goat.server.auth.dto.response;

import com.goat.server.global.domain.type.AccessToken;
import lombok.Builder;
import org.springframework.context.annotation.Bean;

@Builder
public record ReIssueSuccessResponse(
        String accessToken,
        String refreshToken
) {
    public static ReIssueSuccessResponse From(AccessToken token) {
        return ReIssueSuccessResponse.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();
    }
}
