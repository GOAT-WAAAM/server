package com.goat.server.auth.util;

import com.goat.server.auth.domain.type.OAuthProvider;
import com.goat.server.auth.dto.response.OAuthInfoResponse;

public interface OAuthApiClient {
    OAuthProvider oAuthProvider();
    OAuthInfoResponse requestOauthInfo(String accessToken);
}
