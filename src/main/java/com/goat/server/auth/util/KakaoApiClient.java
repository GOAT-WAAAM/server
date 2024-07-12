package com.goat.server.auth.util;

import com.goat.server.auth.domain.type.OAuthProvider;
import com.goat.server.auth.dto.response.KakaoInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoApiClient", url = "${oauth.kakao.service.api_url}")
@Component
public interface KakaoApiClient extends OAuthApiClient {

    @Override
    @GetMapping(value = "/v2/user/me")
    KakaoInfoResponse requestOauthInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken);

    @Override
    default OAuthProvider oAuthProvider() {
        return OAuthProvider.KAKAO;
    }
}
