package com.goat.server.auth.dto.request;

public record OnBoardingRequest(
        String nickname,
        String goal,
        String fcmToken
) {
}
