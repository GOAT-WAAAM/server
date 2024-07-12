package com.goat.server.auth.dto.request;

import com.goat.server.auth.domain.type.OAuthProvider;
import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {
    OAuthProvider oAuthProvider();
    String accessToken();
    MultiValueMap<String, String> makeBody();
}
