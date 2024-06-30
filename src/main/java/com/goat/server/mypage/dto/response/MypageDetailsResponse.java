package com.goat.server.mypage.dto.response;

import com.goat.server.mypage.domain.User;
import com.goat.server.mypage.domain.type.Grade;
import lombok.Builder;

import java.util.List;

@Builder
public record MypageDetailsResponse(
        String imageUrl,
        String nickname,
        Grade grade,
        List<String> majorList
) {
    public static MypageDetailsResponse of(User user, List<String> majorNames) {
        return MypageDetailsResponse.builder()
                .imageUrl(user.getProfileImageUrl())
                .nickname(user.getNickname())
                .grade(user.getGrade())
                .majorList(majorNames)
                .build();
    }
}
