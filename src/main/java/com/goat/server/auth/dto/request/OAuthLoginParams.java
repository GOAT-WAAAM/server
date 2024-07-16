package com.goat.server.auth.dto.request;

import com.goat.server.auth.domain.type.OAuthProvider;

public interface OAuthLoginParams {
    OAuthProvider oAuthProvider();
    String accessToken();
}
