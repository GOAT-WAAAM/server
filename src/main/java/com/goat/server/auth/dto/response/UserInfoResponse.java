package com.goat.server.auth.dto.response;

import com.goat.server.mypage.domain.User;
import lombok.Builder;

@Builder
public record UserInfoResponse(
        Long userId,
        String nickname,
        String goal,
        String profileImgUrl,
        Long totalReviewCnt
) {
    public static UserInfoResponse of(User user, Long totalReviewCnt) {
        return UserInfoResponse.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .goal(user.getGoal())
                .profileImgUrl(user.getProfileImageUrl())
                .totalReviewCnt(totalReviewCnt)
                .build();
    }
}
