package com.goat.server.auth.dto;

public record OnBoardingRequest(
        String nickname,
        String goal
) {
}
