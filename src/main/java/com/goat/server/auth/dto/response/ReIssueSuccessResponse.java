package com.goat.server.auth.dto.response;

import com.goat.server.global.util.Tokens;
import lombok.Builder;

@Builder
public record ReIssueSuccessResponse(
        String accessToken,
        String refreshToken
) {
    public static ReIssueSuccessResponse from(Tokens token) {
        return ReIssueSuccessResponse.builder()
                .accessToken(token.accessToken())
                .refreshToken(token.refreshToken())
                .build();
    }
}
