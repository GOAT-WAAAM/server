package com.goat.server.mypage.dto.response;

import com.goat.server.mypage.domain.type.Grade;

import java.util.List;

public record MypageDetailsResponse(
        String imageUrl,
        String nickname,
        Grade grade,
        List<String> majorList) {
}
