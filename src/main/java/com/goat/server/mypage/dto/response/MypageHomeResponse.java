package com.goat.server.mypage.dto.response;

import com.goat.server.mypage.domain.User;
import lombok.Builder;

@Builder
public record MypageHomeResponse (
        String imageUrl,
        String nickname,
        String goal,
        Long totalReviewCnt
) {
    public static MypageHomeResponse of(User user, Long totalReviewCnt) {
        return MypageHomeResponse.builder()
                .imageUrl(user.getProfileImageUrl())
                .nickname(user.getNickname())
                .goal(user.getGoal())
                .totalReviewCnt(totalReviewCnt)
                .build();
    }
}
