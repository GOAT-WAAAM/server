package com.goat.server.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.goat.server.auth.domain.type.OAuthProvider;
import lombok.Builder;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record KakaoInfoResponse (
        Long id,
        KakaoAccount kakaoAccount
) implements OAuthInfoResponse {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @Builder
    public record KakaoAccount(
            KakaoUserProfile profile
    ) {
        public String getNickname() {
            return profile.nickname();
        }

        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public record KakaoUserProfile(
                String nickname,
                String profileImageUrl
        ) {
        }
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getNickname() {
        return kakaoAccount.getNickname();
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.KAKAO;
    }
}
