package com.goat.server.auth.util;

import com.goat.server.auth.domain.type.OAuthProvider;
import com.goat.server.auth.dto.response.NaverInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "naverApiClient", url = "${oauth.naver.service.api_url}")
@Component
public interface NaverApiClient extends OAuthApiClient {

    @Override
    @GetMapping(value = "/v1/nid/me")
    NaverInfoResponse requestOauthInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken);

    @Override
    default OAuthProvider oAuthProvider() {
        return OAuthProvider.NAVER;
    }
}
