package com.goat.server.auth.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
public record KakaoUserResponse(
        Long id,
        KakaoAccount kakaoAccount
) {
    public String getNickname() {
        return kakaoAccount.getNickname();
    }
}
