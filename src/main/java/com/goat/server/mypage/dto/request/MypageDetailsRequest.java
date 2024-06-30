package com.goat.server.mypage.dto.request;

import com.goat.server.mypage.domain.type.Grade;
import lombok.Builder;

import java.util.List;

@Builder
public record MypageDetailsRequest(
        String nickname,
        Grade grade,
        List<String> majorList
) {
}
