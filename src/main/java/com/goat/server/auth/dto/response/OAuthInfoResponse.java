package com.goat.server.auth.dto.response;

import com.goat.server.auth.domain.type.OAuthProvider;

public interface OAuthInfoResponse {
    Long getId();
    String getNickname();
    OAuthProvider getOAuthProvider();
}
