package com.goat.server.auth.dto.response;

import com.goat.server.global.util.jwt.Tokens;
import com.goat.server.mypage.domain.User;
import lombok.Builder;

@Builder
public record SignUpSuccessResponse(
        String accessToken,
        String refreshToken,
        UserInfo userInfo
) {
    @Builder
    public record UserInfo(
            Long userId,
            String nickname,
            String goal,
            String profileImgUrl

    ) {
        public static UserInfo from(User user) {
            return UserInfo.builder()
                    .userId(user.getUserId())
                    .nickname(user.getNickname())
                    .goal(user.getGoal())
                    .profileImgUrl(user.getProfileImageUrl())
                    .build();
        }
    }

    public static SignUpSuccessResponse from(Tokens token, User user) {
        return SignUpSuccessResponse.builder()
                .accessToken(token.accessToken())
                .refreshToken(token.refreshToken())
                .userInfo(UserInfo.from(user))
                .build();
    }
}
