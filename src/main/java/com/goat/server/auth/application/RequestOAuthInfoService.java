package com.goat.server.auth.application;

import com.goat.server.auth.domain.type.OAuthProvider;
import com.goat.server.auth.dto.request.OAuthLoginParams;
import com.goat.server.auth.dto.response.OAuthInfoResponse;
import com.goat.server.auth.util.OAuthApiClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RequestOAuthInfoService {
    private final Map<OAuthProvider, OAuthApiClient> clients;

    public RequestOAuthInfoService(List<OAuthApiClient> clients) {
        this.clients = clients.stream().collect(
                Collectors.toUnmodifiableMap(OAuthApiClient::oAuthProvider, Function.identity())
        );
    }

    public OAuthInfoResponse request(OAuthLoginParams params) {
        OAuthApiClient client = clients.get(params.oAuthProvider());
        String accessToken = params.accessToken();
        return client.requestOauthInfo(accessToken);
    }
}
