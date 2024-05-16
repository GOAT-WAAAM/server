package com.goat.server.global.domain.type;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class AccessToken {
    final String accessToken;
    final String refreshToken;
}
