package com.goat.server.global.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccessToken {
    final String accessToken;
    final String refreshToken;
}
