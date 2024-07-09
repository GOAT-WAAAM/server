package com.goat.server.mypage.dto.response;

import com.goat.server.mypage.domain.User;
import lombok.Builder;

@Builder
public record MypageDetailsResponse(
        String imageUrl,
        String nickname
) {
    public static MypageDetailsResponse from(User user) {
        return MypageDetailsResponse.builder()
                .imageUrl(user.getProfileImageUrl())
                .nickname(user.getNickname())
                .build();
    }
}
