package com.goat.server.auth.dto.request;

import com.goat.server.auth.domain.type.OAuthProvider;
import org.springframework.util.MultiValueMap;

public record KakaoLoginParams(
        String kakaoAccessToken
) implements OAuthLoginParams {
    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.KAKAO;
    }

    @Override
    public String accessToken() {
        return kakaoAccessToken;
    }
}
