package com.goat.server.mypage.dto.response;

import com.goat.server.mypage.domain.type.Grade;

import java.util.List;

public record MypageHomeResponse (
        String imageUrl,
        String nickname,
        Grade grade,
        String goal,
        List<String> majorList
) {
}
