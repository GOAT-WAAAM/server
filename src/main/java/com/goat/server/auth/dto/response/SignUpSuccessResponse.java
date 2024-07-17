package com.goat.server.auth.dto.response;

import com.goat.server.global.util.jwt.Tokens;
import com.goat.server.mypage.domain.User;
import lombok.Builder;

@Builder
public record SignUpSuccessResponse(
        String accessToken,
        String refreshToken,
        UserInfoResponse userInfo
) {
    public static SignUpSuccessResponse of(Tokens token, User user, Long totalReviewCnt) {
        return SignUpSuccessResponse.builder()
                .accessToken(token.accessToken())
                .refreshToken(token.refreshToken())
                .userInfo(UserInfoResponse.of(user, totalReviewCnt))
                .build();
    }
}
