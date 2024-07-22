package com.goat.server.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.goat.server.auth.domain.type.OAuthProvider;
import lombok.Builder;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record NaverInfoResponse(

        Response response

) implements OAuthInfoResponse {

    @Override
    public Long getId() {
        return response.id();
    }

    @Override
    public String getNickname() {
        return response.nickname();
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.NAVER;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Response(
            Long id,
            String nickname
    ) {
    }
}
