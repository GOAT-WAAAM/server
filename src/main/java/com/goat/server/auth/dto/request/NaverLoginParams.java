package com.goat.server.auth.dto.request;

import com.goat.server.auth.domain.type.OAuthProvider;

public record NaverLoginParams(
        String naverAccessToken
) implements OAuthLoginParams {

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.NAVER;
    }

    @Override
    public String accessToken() {
        return naverAccessToken;
    }
}
