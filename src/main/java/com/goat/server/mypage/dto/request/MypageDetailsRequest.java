package com.goat.server.mypage.dto.request;

import lombok.Builder;

@Builder
public record MypageDetailsRequest(
        String nickname
) {
}
